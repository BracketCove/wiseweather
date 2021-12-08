package com.bracketcove.wiseweather.ui.currentweather

import com.bracketcove.wiseweather.domain.Weather

sealed class UiState {
    data class CurrentWeather(val weather: Weather) : UiState()
    data class Error(val e: ErrorType) : UiState()
    object Loading : UiState()
}

//Keeps String resources out of Presentation logic
enum class ErrorType {
    PERMISSION,
    IO
}