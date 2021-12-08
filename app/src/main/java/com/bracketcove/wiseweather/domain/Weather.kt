package com.bracketcove.wiseweather.domain

import android.os.Parcel
import android.os.Parcelable

/**
 * @property weather simple name of weather pattern
 * @property weatherDescription descriptive name of weather pattern
 * @property temp in user's preferred Unit
 * @property windspeed in user's preferred Unit
 * @property windDirection 360 meteorological degrees
 * @property cloudiness as a percentage 1-100
 * @property timeZone shift in seconds from UTC
 * @property locationName based on coordinates
 */
data class Weather(
    val weather: WeatherType = WeatherType.CLEAR,
    val weatherDescription: String = "",
    val temp: Double = 0.0,
    val windspeed: Double = 0.0,
    val windDirection: Int = 0,
    val cloudiness: Int = 0,
    val timeZone: Int = 0,
    val locationName: String = "",
    val sunset: Int = 0,
    val sunrise: Int = 0,
    val country: String = "",
    val timestamp : Long = 0L
) : Parcelable {
    constructor(parcel: Parcel) : this(
        WeatherType.values()[parcel.readInt()],
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(weather.ordinal)
        parcel.writeString(weatherDescription)
        parcel.writeDouble(temp)
        parcel.writeDouble(windspeed)
        parcel.writeInt(windDirection)
        parcel.writeInt(cloudiness)
        parcel.writeInt(timeZone)
        parcel.writeString(locationName)
        parcel.writeInt(sunset)
        parcel.writeInt(sunrise)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}

fun String.getWeatherType(): WeatherType {
    return when (this) {
        "Clear" -> WeatherType.CLEAR
        "Thunderstorm" -> WeatherType.THUNDERSTORM
        "Drizzle" -> WeatherType.DRIZZLE
        "Rain" -> WeatherType.RAIN
        "Snow" -> WeatherType.SNOW
        "Mist" -> WeatherType.MIST
        "Smoke" -> WeatherType.SMOKE
        "Haze" -> WeatherType.HAZE
        "Sand" -> WeatherType.SAND
        "Ash" -> WeatherType.ASH
        "Fog" -> WeatherType.FOG
        "Dust" -> WeatherType.DUST
        "Clouds" -> WeatherType.CLOUDS
        else -> WeatherType.TORNADO
    }
}


