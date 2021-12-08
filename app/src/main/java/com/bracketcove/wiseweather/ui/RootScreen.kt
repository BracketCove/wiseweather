package com.bracketcove.wiseweather.ui

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import com.bracketcove.wiseweather.R
import com.bracketcove.wiseweather.domain.UOM
import com.bracketcove.wiseweather.ui.currentweather.CurrentWeatherScreen
import com.bracketcove.wiseweather.ui.currentweather.ErrorType
import com.bracketcove.wiseweather.ui.currentweather.UiState

@ExperimentalComposeUiApi
@Composable
fun RootScreen(
    viewModel: UiStateViewModel,
    eventHandler: (UiEvent) -> Unit
) {
    val uiState = remember {
        MutableTransitionState<UiState>(
            viewModel.uiState
        )
    }

    viewModel.observeUiState = {
        uiState.targetState = it
    }

    val transition = updateTransition(uiState)

    val errorAlpha by transition.animateFloat(
        { tween(300) }
    ) {
        if (it is UiState.Error) 1f else 0f
    }

    val loadingAlpha by transition.animateFloat(
        { tween(300) }
    ) {
        if (it is UiState.Loading) 1f else 0f
    }

    val currentAlpha by transition.animateFloat(
        { tween(300) }
    ) {
        if (it is UiState.CurrentWeather) 1f else 0f
    }

    Box {
        when (val currentState = uiState.currentState) {
            is UiState.CurrentWeather -> Box(Modifier.alpha(currentAlpha)) {
                CurrentWeatherScreen(currentState.weather)
            }
            is UiState.Error -> Box(Modifier.alpha(errorAlpha)) {
                ErrorScreen(eventHandler, currentState.e.toLocalizedMessage())
            }
            is UiState.Loading -> Box(Modifier.alpha(loadingAlpha)) {
                LoadingScreen()
            }
        }
    }
}

@Composable
fun ErrorType.toLocalizedMessage(): String = when (this) {
    ErrorType.PERMISSION -> stringResource(R.string.location_perm)
    ErrorType.IO -> stringResource(R.string.error)
}
