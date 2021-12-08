package com.bracketcove.wiseweather.ui

import com.bracketcove.wiseweather.ui.currentweather.UiState

class UiStateViewModel(
    var lat: Double = 0.0,
    var lon: Double = 0.0
) {
    var uiState: UiState = UiState.Loading
        set(value) {
            //only update if necessary
            if (value != field) {
                field = value
                observeUiState?.invoke(value)
            }
        }

    var observeUiState: ((UiState) -> Unit)? = null
}