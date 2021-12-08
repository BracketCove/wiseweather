package com.bracketcove.wiseweather.weatherapi

import android.util.Log
import com.bracketcove.wiseweather.BuildConfig
import com.bracketcove.wiseweather.domain.IWeatherApi
import com.bracketcove.wiseweather.domain.WeatherResult
import com.bracketcove.wiseweather.util.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

private const val API_URL = "https://api.openweathermap.org/data/2.5/weather?"
private const val QUERY_SEP = "&"
private const val LAT = "lat="
private const val LON = "lon="
private const val API_KEY = BuildConfig.OWM_KEY
private const val APP_ID = "appid="

class OpenWeatherMapApiImpl : IWeatherApi {
    override suspend fun getWeather(lat: Double, lon: Double, lang: String): WeatherResult = withContext(
        Dispatchers.IO){
        val encLon = URLEncoder.encode(lon.toString(), "UTF-8")
        val encLat = URLEncoder.encode(lat.toString(), "UTF-8")

        val url = URL(
            API_URL + LAT + encLat + QUERY_SEP + LON + encLon + QUERY_SEP + APP_ID + API_KEY
        )

        val httpCon = url.openConnection() as HttpURLConnection

        return@withContext try {
            WeatherResult.SUCCESS(
                JsonParser.parseJsonStringForWeather(
                    httpCon.inputStream.bufferedReader().readText()
                )
            )

        } catch (e: Exception) {
            WeatherResult.ERROR(e)
        } finally {
            httpCon.disconnect()
        }
    }
}
