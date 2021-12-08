package com.bracketcove.wiseweather.ui.currentweather

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.bracketcove.wiseweather.domain.UOM
import com.bracketcove.wiseweather.domain.Weather
import com.bracketcove.wiseweather.ui.theme.headerBold
import com.bracketcove.wiseweather.ui.theme.headerNormal
import com.bracketcove.wiseweather.util.isNight

@ExperimentalComposeUiApi
@Composable
fun CurrentWeatherScreen(
    weather: Weather
) {
    BoxWithConstraints {
        val constraints = if (minWidth < minHeight) {
            portraitConstraintSet()
        } else {
            landscapeConstraintSet()
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(),
            constraintSet = constraints
        ) {
            WeatherBackground(
                modifier = Modifier.layoutId("bg"),
                weather = weather,
                isNight(weather.sunrise, weather.sunset)
            )

            TitleText(
                modifier = Modifier.layoutId("title")
                    .padding(8.dp)
            )

            WeatherInfo(
                Modifier.layoutId("info")
                    .wrapContentHeight(),
                weather
            )
        }

    }

}

@Composable
fun TitleText(
    modifier: Modifier
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "wise",
            style = headerNormal
        )
        Text(
            text = "Weather",
            style = headerBold
        )
    }
}

@ExperimentalComposeUiApi
private fun portraitConstraintSet(): ConstraintSet = ConstraintSet {
    val title = createRefFor("title")
    val bg = createRefFor("bg")
    val info = createRefFor("info")

    constrain(bg) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }

    constrain(title) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(info) {
        bottom.linkTo(parent.bottom)
    }
}

@ExperimentalComposeUiApi
private fun landscapeConstraintSet(): ConstraintSet = ConstraintSet {
    val title = createRefFor("title")
    val bg = createRefFor("bg")
    val info = createRefFor("info")

    constrain(bg) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }

    constrain(title) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(info) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }
}
