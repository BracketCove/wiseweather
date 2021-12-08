package com.bracketcove.wiseweather.util

import android.util.JsonReader
import android.util.JsonToken
import com.bracketcove.wiseweather.domain.Weather
import com.bracketcove.wiseweather.domain.getWeatherType
import java.io.StringReader

object JsonParser {

    fun parseJsonStringForWeather(json: String): Weather {
        try {
            val reader = JsonReader(StringReader(json))

            var weather = Weather()

            reader.beginObject()

            while (reader.hasNext()) {
                val update: Weather
                when (reader.nextName()) {
                    //note weather is an array
                    "weather" -> {
                        update = readWeather(reader)
                        weather = weather.copy(
                            weather = update.weather,
                            weatherDescription = update.weatherDescription
                        )
                    }
                    "main" -> {
                        update = readMain(reader)
                        weather = weather.copy(
                            temp = update.temp
                        )
                    }
                    "wind" -> {
                        update = readWind(reader)
                        weather = weather.copy(
                            windspeed = update.windspeed,
                            windDirection = update.windDirection
                        )
                    }
                    "clouds" -> {
                        update = readClouds(reader)
                        weather = weather.copy(
                            cloudiness = update.cloudiness
                        )
                    }
                    "sys" -> {
                        update = readSys(reader)
                        weather = weather.copy(
                            sunrise = update.sunrise,
                            sunset = update.sunset,
                            country = update.country
                        )
                    }
                    "timezone" -> weather = weather.copy(timeZone = reader.nextInt())
                    "name" -> weather = weather.copy(locationName = reader.nextString())
                    else -> reader.skipValue()
                }
            }

            reader.endObject()

            return weather.copy(timestamp = System.currentTimeMillis())
        } catch (e: Exception) {
            throw e
        }
    }

    private fun readMain(reader: JsonReader): Weather {
        var temp = 0.0

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "temp" -> temp = reader.nextDouble()
                else -> reader.skipValue()
            }
        }

        reader.endObject()

        return Weather(temp = temp)
    }

    private fun readClouds(reader: JsonReader): Weather {
        var cloudiness = 0

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "all" -> cloudiness = reader.nextInt()
                else -> reader.skipValue()
            }
        }

        reader.endObject()

        return Weather(cloudiness = cloudiness)
    }

    /**
     * Get weather name and weather description
     */
    private fun readWeather(reader: JsonReader): Weather {
        var name = ""
        var desc = ""

        reader.beginArray()
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "main" -> name = reader.nextString()
                "description" -> desc = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        //TODO: skip remaining weather patterns until we can implement support for them
        while (reader.peek() == JsonToken.BEGIN_OBJECT) {
            reader.beginObject()
            while (reader.peek() != JsonToken.END_OBJECT) {
                reader.skipValue()
            }
            reader.endObject()
        }

        reader.endArray()
        return Weather(weather = name.getWeatherType(), weatherDescription = desc)
    }

    private fun readSys(reader: JsonReader): Weather {
        var sunrise = 0
        var sunset = 0
        var country = ""

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "sunrise" -> sunrise = reader.nextInt()
                "sunset" -> sunset = reader.nextInt()
                "country" -> country = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return Weather(sunrise = sunrise, sunset = sunset, country = country)
    }


    private fun readWind(reader: JsonReader): Weather {
        var speed = 0.0
        var dir = 0

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "speed" -> speed = reader.nextDouble()
                "deg" -> dir = reader.nextInt()
                else -> reader.skipValue()
            }
        }

        reader.endObject()

        return Weather(windspeed = speed, windDirection = dir)
    }
}