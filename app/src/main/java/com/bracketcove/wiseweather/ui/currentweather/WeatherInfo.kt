package com.bracketcove.wiseweather.ui.currentweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bracketcove.wiseweather.R
import com.bracketcove.wiseweather.domain.UOM
import com.bracketcove.wiseweather.domain.Weather
import com.bracketcove.wiseweather.ui.theme.headerBold
import com.bracketcove.wiseweather.ui.theme.headerNormal
import com.bracketcove.wiseweather.util.kelvinToCelcius
import com.bracketcove.wiseweather.util.kelvinToFahrenheit
import com.bracketcove.wiseweather.util.metersPerSecondToKmpHour
import com.bracketcove.wiseweather.util.metersPerSecondToMpHour
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun WeatherInfo(
    modifier: Modifier,
    weather: Weather
) {
    //TODO implement uom specific output
    ConstraintLayout(modifier) {
        val (locationName, weatherDesc, divider, tempIcon, temp, windIcon, wind) = createRefs()

        Text(
            text = weather.locationName,
            style = headerBold,
            modifier = Modifier
                .wrapContentHeight()
                .constrainAs(locationName) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                }
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = weather.weatherDescription,
            style = headerBold,
            modifier = Modifier
                .wrapContentHeight()
                .constrainAs(weatherDesc) {
                    top.linkTo(locationName.bottom)
                    centerHorizontallyTo(parent)
                }
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(R.drawable.ic_thermostat_white_48dp_1),
            contentDescription = "Temperature",
            modifier = Modifier
                .size(64.dp)
                .constrainAs(tempIcon) {
                    top.linkTo(weatherDesc.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(divider.start)
                }
        )

        Text(
            text = if (weather.country.isImperialCountry()) weather.temp.kelvinToFahrenheit()
            else weather.temp.kelvinToCelcius(),
            style = headerBold.copy(fontSize = 31.sp),
            modifier = Modifier
                .constrainAs(temp) {
                    top.linkTo(tempIcon.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(divider.start)
                    bottom.linkTo(parent.bottom)
                }
                .padding(16.dp)
        )

        Divider(
            Modifier
                .width(1.dp)
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = Color.Red
        )

        Image(
            painter = painterResource(R.drawable.ic_air_white_48dp),
            contentDescription = "Wind Speed",
            modifier = Modifier
                .size(64.dp)
                .constrainAs(windIcon) {
                    top.linkTo(weatherDesc.bottom)
                    start.linkTo(divider.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = if (weather.country.isImperialCountry()) weather.windspeed.metersPerSecondToMpHour()
            else weather.windspeed.metersPerSecondToKmpHour(),
            style = headerBold.copy(fontSize = 31.sp),
            modifier = Modifier
                .constrainAs(wind) {
                    top.linkTo(windIcon.bottom)
                    start.linkTo(divider.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(
                    top = 16.dp,
                    bottom = 16.dp,
                    end = 32.dp
                )
        )
    }
}

/**
 * For the time being, we check the country code to determine whether to display in metric or not
 */
private fun String.isImperialCountry(): Boolean = when (this) {
    "US", "LR", "MM" -> true
    else -> false
}


