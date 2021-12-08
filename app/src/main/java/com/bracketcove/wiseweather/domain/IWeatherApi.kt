package com.bracketcove.wiseweather.domain

interface IWeatherApi {
    suspend fun getWeather(lat: Double, lon: Double, lang: String): WeatherResult
}

sealed class WeatherResult {
    data class SUCCESS(val weatherResult: Weather): WeatherResult()
    data class ERROR(val e: Exception): WeatherResult()
}