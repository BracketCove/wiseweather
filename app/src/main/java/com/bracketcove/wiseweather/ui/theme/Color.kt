package com.bracketcove.wiseweather.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


val Day = Color(0xFF3598DB)
val Night = Color(0xFF285679)

//light palette
val Pink = Color(0xFFF48FB1)
val PinkDark = Color(0xFFBF5F82)
val PinkLight = Color(0xFFFFC1E3)

val Teal200 = Color(0xFF03DAC5)

internal val LightColorPalette = lightColors(
    primary = Pink,
    primaryVariant = PinkDark,
    secondary = Teal200,
    background = PinkLight
)

//dark palette
val Blue = Color(0xFF0d47a1)
val BlueDark = Color(0xFF002171)
val BlueLight = Color(0xFF5472d3)

val Cyan800 = Color(0xFF00838f)

internal val DarkColorPalette = darkColors(
    primary = Blue,
    primaryVariant = BlueDark,
    secondary = Cyan800,
    background = BlueLight
)