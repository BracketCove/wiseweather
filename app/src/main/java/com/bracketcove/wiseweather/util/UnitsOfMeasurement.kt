package com.bracketcove.wiseweather.util

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * 0K − 273.15 = -273.1°C
 */
fun Double.kelvinToCelcius(): String {
    val c = this - 273.15
    return BigDecimal(c)
        .setScale(1, RoundingMode.HALF_DOWN).toString() + "°C"
}

/**
 * (0K − 273.15) × 9/5 + 32 = -459.7°F
 */
fun Double.kelvinToFahrenheit(): String {
    val f = (this - 273.15) * (9/5) + 32
    return BigDecimal(f)
        .setScale(1, RoundingMode.HALF_DOWN).toString() + "°F"
}

/**
 * multiply by factor of 3.6
 */
fun Double.metersPerSecondToKmpHour(): String {
    val kmph = this * 3.6
    return BigDecimal(kmph)
        .setScale(1, RoundingMode.HALF_DOWN).toString() + "km/h"
}

/**
 * Double.multiply by factor of 2.23694
 */
fun Double.metersPerSecondToMpHour(): String {
    val mph = this * 2.23694
    return BigDecimal(mph)
        .setScale(1, RoundingMode.HALF_DOWN).toString() + "mph"
}