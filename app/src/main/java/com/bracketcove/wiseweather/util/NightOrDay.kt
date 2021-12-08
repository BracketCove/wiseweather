package com.bracketcove.wiseweather.util

/**
 * Sunrise and sunset come in as milliseconds from UTC.
 * e.g.
 * sunrise - 1560343627
 * sunset - 1560396563
 *
 * System.currentTimeMillis() returns a much longer integer than the API, so we substring the first
 * 10 numbers.
 */
internal fun isNight(sunrise: Int, sunset: Int): Boolean {
    val currentTime = System.currentTimeMillis().toString().substring(0, 10).toInt()
    return if (currentTime > sunrise && currentTime < sunset) false
    else true
}