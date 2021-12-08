package com.bracketcove.wiseweather.ui

import com.bracketcove.wiseweather.weatherapi.OpenWeatherMapApiImpl
import kotlinx.coroutines.Dispatchers

fun Container.buildLogic(viewModel: UiStateViewModel): PresentationLogic =
    PresentationLogic(
        OpenWeatherMapApiImpl(),
        Dispatchers.Main,
        viewModel,
        this
    )