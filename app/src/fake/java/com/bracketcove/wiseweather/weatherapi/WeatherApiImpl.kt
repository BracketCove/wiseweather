package com.bracketcove.wiseweather.weatherapi

import com.bracketcove.wiseweather.domain.IWeatherApi
import com.bracketcove.wiseweather.domain.Weather
import com.bracketcove.wiseweather.domain.WeatherResult
import com.bracketcove.wiseweather.domain.WeatherType
import com.bracketcove.wiseweather.util.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class OpenWeatherMapApiImpl : IWeatherApi {

    @Volatile
    var showError = false

    override suspend fun getWeather(lat: Double, lon: Double, lang: String): WeatherResult =
        withContext(
            Dispatchers.IO
        ) {

//            try {
//                WeatherResult.SUCCESS(JsonParser.parseJsonStringForWeather(testString))
//            } catch (e: Exception) {
//                WeatherResult.ERROR(e)
//            }

            if (showError) {
                showError = false
                WeatherResult.ERROR(Exception())
            } else WeatherResult.SUCCESS(
                testRainyDay.copy(timestamp = 1638476417258 - 3600000)
            )
        }
}

val testString =
    "{\"coord\":{\"lon\":-114.0853,\"lat\":51.0501},\"weather\":[{\"id\":521,\"main\":\"Rain\",\"description\":\"shower rain\",\"icon\":\"09n\"}," +
            "{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}," +
            "{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}],\"base\":\"stations\"," +
            "\"main\":{\"temp\":260.78,\"feels_like\":253.78,\"temp_min\":257.75,\"temp_max\":262.44,\"pressure\":1025,\"humidity\":80}," +
            "\"visibility\":6437,\"wind\":{\"speed\":4.02,\"deg\":61,\"gust\":5.36},\"clouds\":{\"all\":90},\"dt\":1638711661," +
            "\"sys\":{\"type\":2,\"id\":2003474,\"country\":\"CA\",\"sunrise\":1638717813,\"sunset\":1638747064},\"timezone\":-25200,\"id\":5913490,\"name\":\"Calgary\",\"cod\":200}"

/*
NOTE: Day and Night are calculated based on sunrise and sunset returned from the API; you may need
to adjust the values before testing!

//TODO fix day and night testing so I don't have to keep recalculating it
 */


val testThunderDay = Weather(
    WeatherType.THUNDERSTORM,
    weatherDescription = "Thunderstorm",
    temp = 300.01,
    windspeed = 40.0,
    windDirection = 90,
    cloudiness = 90,
    0,
    "Baltimore",
    sunset = 1638574318,
    sunrise = 1638544862,
    country = "US"
)

val testSnowDay = Weather(
    WeatherType.SNOW,
    weatherDescription = "Snowfall",
    temp = 230.01,
    windspeed = 10.0,
    windDirection = 90,
    cloudiness = 60,
    0,
    "Edmonton",
    sunset = 1737856008,
    sunrise = 1637025718
)

val testSnowNight = Weather(
    WeatherType.SNOW,
    weatherDescription = "Snowfall",
    temp = 277.01,
    windspeed = 10.0,
    windDirection = 90,
    cloudiness = 90,
    0,
    "London",
    sunset = 1637856008,
    sunrise = 1637825718
)

val testFogNight = Weather(
    WeatherType.FOG,
    weatherDescription = "Foggy",
    temp = 277.01,
    windspeed = 100.0,
    windDirection = 90,
    cloudiness = 10,
    0,
    "London",
    sunset = 1637856008,
    sunrise = 1637825718
)

val testFogDay = Weather(
    WeatherType.FOG,
    weatherDescription = "Foggy",
    temp = 277.01,
    windspeed = 100.0,
    windDirection = 90,
    cloudiness = 10,
    0,
    "London",
    sunset = 1737856008,
    sunrise = 1637025718
)

val testCloudNight = Weather(
    WeatherType.CLOUDS,
    weatherDescription = "Broken Clouds",
    temp = 277.01,
    windspeed = 35.0,
    windDirection = 90,
    cloudiness = 0,
    0,
    "London",
    sunset = 1637856008,
    sunrise = 1637825718
)

val testCloudyDay = Weather(
    WeatherType.CLOUDS,
    weatherDescription = "Broken Clouds",
    temp = 277.01,
    windspeed = 10.0,
    windDirection = 90,
    cloudiness = 40,
    0,
    "London",
    sunset = 1737856008,
    sunrise = 1637025718
)

val testClearDay = Weather(
    WeatherType.CLEAR,
    weatherDescription = "Clear Sky",
    temp = 277.01,
    windspeed = 0.0,
    windDirection = 90,
    cloudiness = 0,
    0,
    "London",
    sunset = 1737856008,
    sunrise = 1637025718
)

val testClearNight = Weather(
    WeatherType.CLEAR,
    weatherDescription = "Clear Sky",
    temp = 277.01,
    windspeed = 0.0,
    windDirection = 90,
    cloudiness = 0,
    0,
    "London",
    sunset = 1637856008,
    sunrise = 1637825718
)

val testRainyDay = Weather(
    WeatherType.DRIZZLE,
    weatherDescription = "Light Rain",
    temp = 277.01,
    windspeed = 0.0,
    windDirection = 90,
    cloudiness = 30,
    0,
    "London",
    sunset = 1638287762,
    sunrise = 1638258166
)

val testRainyNight = Weather(
    WeatherType.RAIN,
    weatherDescription = "Rain",
    temp = 277.01,
    windspeed = 0.0,
    windDirection = 90,
    cloudiness = 20,
    0,
    "Victoria",
    sunset = 1637856008,
    sunrise = 1637025718
)