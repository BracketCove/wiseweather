package com.bracketcove.wiseweather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.ActivityCompat
import com.bracketcove.wiseweather.R
import com.bracketcove.wiseweather.domain.Weather
import com.bracketcove.wiseweather.ui.currentweather.ErrorType
import com.bracketcove.wiseweather.ui.currentweather.UiState
import com.bracketcove.wiseweather.ui.theme.WiseweatherTheme
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private const val PERM_REQ_CODE = 999
private const val TAG = "MainActivity"
private const val LON = "LON"
private const val LAT = "LAT"

interface Container {
    fun restart()
}

class MainActivity : AppCompatActivity(), Container {

    lateinit var logic: PresentationLogic
    private val viewModel: UiStateViewModel = UiStateViewModel()
    private lateinit var locationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (savedInstanceState != null) {
            val weather: Weather? = savedInstanceState.getParcelable(TAG)
            val lon: Double = savedInstanceState.getDouble(LON)
            val lat: Double = savedInstanceState.getDouble(LAT)

            if (weather != null) {
                viewModel.lon = lon
                viewModel.lat = lat
                viewModel.uiState = UiState.CurrentWeather(weather)
            }
        }

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        logic = buildLogic(viewModel)
    }

    override fun restart() {
        start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val uiState = viewModel.uiState
        if (uiState is UiState.CurrentWeather) {
            outState.putParcelable(
                TAG,
                uiState.weather
            )

            outState.putDouble(LON, viewModel.lon)
            outState.putDouble(LAT, viewModel.lat)
        }
    }

    //I delegate to start() here because the error screen needs to be able to
    //initiate a request as well.
    @ExperimentalComposeUiApi
    override fun onStart() {
        super.onStart()

        setContent {
            WiseweatherTheme {
                RootScreen(
                    viewModel = viewModel,
                    eventHandler = logic::onEvent
                )
            }
        }

        if (viewModel.uiState !is UiState.CurrentWeather) {
            start()
        } else {
            val storedTime = (viewModel.uiState as UiState.CurrentWeather).weather.timestamp
            /*
            1,800,000 ms in one half-hour. If the difference of old - current > 1,800,000
             */
            if (System.currentTimeMillis() - storedTime > 1800000){
                start()
            }
        }
    }

    private fun start() {
        if (!checkPermissions()) {
            startLocationPermissionRequest()
        } else {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationClient.lastLocation
            .addOnCompleteListener { taskLocation ->
                if (taskLocation.isSuccessful && taskLocation.result != null) {

                    val location = taskLocation.result

                    viewModel.lat = location.latitude
                    viewModel.lon = location.longitude

                    logic.onEvent(UiEvent.RequestData)

                } else {
                    if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == 0) {
                        Toast.makeText(this, R.string.google_serv, Toast.LENGTH_LONG ).show()
                    }
                    logic.onEvent(
                        UiEvent.OnError(
                            ErrorType.IO
                        )
                    )
                }
            }
    }

    /**
     * Return the current state of the permissions needed.
     */
    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            PERM_REQ_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERM_REQ_CODE) {
            when {
                // Permission granted.
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLocation()

                else -> {
                    logic.onEvent(
                        UiEvent.OnError(
                            ErrorType.PERMISSION
                        )
                    )
                }
            }
        }
    }
}

