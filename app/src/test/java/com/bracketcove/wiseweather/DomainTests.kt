package com.bracketcove.wiseweather

import com.bracketcove.wiseweather.domain.Weather
import com.bracketcove.wiseweather.domain.WeatherType
import com.bracketcove.wiseweather.util.JsonParser
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class JsonParsingTest {
    @Test
    fun parseSample() {
        val testString =
            "{\"coord\":{\"lon\":-114.0853,\"lat\":51.0501},\"weather\":[{\"id\":521,\"main\":\"Rain\",\"description\":\"shower rain\",\"icon\":\"09n\"},{\"id\":600,\"main\":\"Snow\"," +
                    "\"description\":\"light snow\",\"icon\":\"13n\"}],\"base\":\"stations\",\"main\":{\"temp\":260.78,\"feels_like\":253.78,\"temp_min\":257.75,\"temp_max\":262.44," +
                    "\"pressure\":1025,\"humidity\":80},\"visibility\":6437,\"wind\":{\"speed\":4.02,\"deg\":61,\"gust\":5.36},\"clouds\":{\"all\":90},\"dt\":1638711661," +
                    "\"sys\":{\"type\":2,\"id\":2003474,\"country\":\"CA\",\"sunrise\":1638717813,\"sunset\":1638747064},\"timezone\":-25200,\"id\":5913490,\"name\":\"Calgary\",\"cod\":200}"


        val expectedResult = Weather(
            weather = WeatherType.RAIN,
            weatherDescription = "shower rain",
            temp = 260.78,
            windspeed = 4.02,
            windDirection = 61,
            cloudiness = 90,
            timeZone = -25200,
            locationName = "Calgary",
        )

        assertEquals(expectedResult, JsonParser.parseJsonStringForWeather(testString))

    }
}