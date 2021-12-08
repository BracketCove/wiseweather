package com.bracketcove.wiseweather.ui

import com.bracketcove.wiseweather.ui.currentweather.ErrorType

sealed class UiEvent {
    object Restart: UiEvent()
    object RequestData: UiEvent()
    data class OnError(val error: ErrorType) : UiEvent()
}

