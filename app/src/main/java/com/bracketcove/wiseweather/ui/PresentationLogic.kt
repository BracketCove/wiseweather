package com.bracketcove.wiseweather.ui

import com.bracketcove.wiseweather.domain.IWeatherApi
import com.bracketcove.wiseweather.domain.WeatherResult
import com.bracketcove.wiseweather.ui.currentweather.ErrorType
import com.bracketcove.wiseweather.ui.currentweather.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PresentationLogic(
    private val weather: IWeatherApi,
    private val uiContext: CoroutineContext,
    private val viewModel: UiStateViewModel,
    private val container: Container?
) : CoroutineScope {

    internal fun onEvent(e: UiEvent) {
        when (e) {
            is UiEvent.RequestData -> {
                viewModel.uiState = UiState.Loading
                request()
            }
            is UiEvent.OnError -> viewModel.uiState = UiState.Error(
                e.error
            )
            UiEvent.Restart -> container?.restart()
        }
    }

    private fun request() = launch {
        val result = weather.getWeather(viewModel.lat, viewModel.lon, "en")

        when (result) {
            is WeatherResult.SUCCESS -> viewModel.uiState = UiState.CurrentWeather(result.weatherResult)
            is WeatherResult.ERROR -> viewModel.uiState = UiState.Error(ErrorType.IO)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = uiContext
}