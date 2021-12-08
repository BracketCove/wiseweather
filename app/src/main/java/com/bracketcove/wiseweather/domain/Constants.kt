package com.bracketcove.wiseweather.domain

enum class UOM {
    METRIC,
    IMPERIAL
}

//Based on API docs for 2.5

/**
 * Weather.weather (weather.main in the current API), comes in one of:
 * "Thunderstorm"
 * "Drizzle"
 * "Rain"
 * "Snow"
 * "Mist"
 * "Clear"
 * "Smoke"
 * "Haze"
 * "Dust"
 * "Fog"
 * "Sand"
 * "Ash"
 * "Tornado"
 * "Clouds"
 *
 * Yes, enums are fine sometimes; despite what outdated/innaccurate stackoverflow answers may say.
 *
 */
enum class WeatherType {
    THUNDERSTORM,
    DRIZZLE,
    RAIN,
    SNOW,
    MIST,
    CLEAR,
    SMOKE,
    HAZE,
    DUST,
    FOG,
    SAND,
    ASH,
    TORNADO,
    CLOUDS
}