package com.bracketcove.wiseweather.ui.currentweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import com.bracketcove.wiseweather.R
import com.bracketcove.wiseweather.domain.Weather
import com.bracketcove.wiseweather.domain.WeatherType
import com.bracketcove.wiseweather.ui.animations.*
import com.bracketcove.wiseweather.ui.theme.Day
import com.bracketcove.wiseweather.ui.theme.Night


internal const val ICON_SIZE = 200f
private const val PRECIP_ANIM = "precipAnim"
private const val SKY_ANIM = "skyAnim"
private const val CLOUD_ANIM = "cloudAnim"
private const val ICON = "icon"
private const val DIVIDER = "divider"
private const val BACKGROUND = "background"


@ExperimentalComposeUiApi
private fun portraitConstraintSet(): ConstraintSet = ConstraintSet {

    val precipAnim = createRefFor(PRECIP_ANIM)
    val cloudAnim = createRefFor(CLOUD_ANIM)
    val skyAnim = createRefFor(SKY_ANIM)
    val icon = createRefFor(ICON)
    val divider = createRefFor(DIVIDER)
    val background = createRefFor(BACKGROUND)

    constrain(background) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }

    constrain(precipAnim) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
    }

    constrain(cloudAnim) {
        bottom.linkTo(divider.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(skyAnim) {
        top.linkTo(parent.top)
        bottom.linkTo(divider.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        height = Dimension.fillToConstraints
        width = Dimension.fillToConstraints
    }

    constrain(icon) {
        bottom.linkTo(divider.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(divider) {
        centerVerticallyTo(parent)
    }
}

@ExperimentalComposeUiApi
private fun landscapeConstraintSet(): ConstraintSet = ConstraintSet {

    val precipAnim = createRefFor(PRECIP_ANIM)
    val cloudAnim = createRefFor(CLOUD_ANIM)
    val skyAnim = createRefFor(SKY_ANIM)
    val icon = createRefFor(ICON)
    val divider = createRefFor(DIVIDER)
    val background = createRefFor(BACKGROUND)

    constrain(background) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }

    constrain(precipAnim) {
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        height = Dimension.fillToConstraints
    }

    constrain(cloudAnim) {
        bottom.linkTo(divider.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(skyAnim) {
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        end.linkTo(parent.end)
        start.linkTo(divider.end)
        height = Dimension.fillToConstraints
        width = Dimension.fillToConstraints
    }

    constrain(icon) {
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        end.linkTo(parent.end)
        start.linkTo(divider.end)
    }

    constrain(divider) {
        centerHorizontallyTo(parent)
        width = Dimension.wrapContent
    }
}

@ExperimentalComposeUiApi
@Composable
fun WeatherBackground(
    modifier: Modifier,
    weather: Weather,
    isNight: Boolean
) {
    BoxWithConstraints {
        val layoutHeight = maxHeight
        val layoutWidth = maxWidth

        val constraints = if (maxWidth < maxHeight) {
            portraitConstraintSet()
        } else {
            landscapeConstraintSet()
        }

        ConstraintLayout(constraints) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .layoutId(BACKGROUND)
            ) {
                //Backgrounds include:
                //Fog background (can be night or day)
                //Night background color
                //Day background color
                if (!isFogbackground(weather.weather)) Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = if (isNight) Night else Day
                ) {} else Box(
                    modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    if (isNight) Night else Day,
                                    Color.Gray
                                )
                            )
                        )
                )
            }

            when (weather.weather) {
                WeatherType.CLEAR -> SkyAnimation(
                    Modifier.layoutId(SKY_ANIM),
                    isNight
                )

                //use sky anim for this too
                WeatherType.THUNDERSTORM -> ThunderAnimation(
                    Modifier.layoutId(SKY_ANIM),
                    maxWidth = layoutWidth.value,
                    maxHeight = layoutHeight.value
                )

                else -> {
                    WeatherIcon(
                        modifier = Modifier
                            .layoutId(ICON)
                            .size(ICON_SIZE.dp),
                        weather,
                        isNight
                    )
                }
            }
            if (weather.weather == WeatherType.RAIN
                || weather.weather == WeatherType.SNOW
                || weather.weather == WeatherType.DRIZZLE
            ) {
                PrecipitationAnimation(
                    modifier = Modifier.layoutId(PRECIP_ANIM).fillMaxHeight(),
                    weather = weather
                )
            }

            if (!isFogbackground(weather.weather)
                && weather.weather != WeatherType.CLEAR
                && weather.cloudiness > 5
            ) {
                CloudAnimations(
                    Modifier.layoutId(CLOUD_ANIM),
                    weather
                )
            }

            Divider(
                modifier = if (minWidth < minHeight) {
                    Modifier
                        .layoutId(DIVIDER)
                        .height(1.dp)
                } else {
                    Modifier
                        .layoutId(DIVIDER)
                        .width(1.dp)
                },
                color = Color.Transparent
            )
        }
    }
}

@Composable
fun ThunderAnimation(
    modifier: Modifier,
    maxWidth: Float,
    maxHeight: Float
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        LightningAnimationContainer(maxWidth = maxWidth, maxHeight = maxHeight)
    }
}

@Composable
fun CloudAnimations(
    modifier: Modifier,
    weather: Weather
) {
    Box(
        modifier
    ) {
        CloudAnimationContainer(windSpeed = weather.windspeed, cloudiness = weather.cloudiness)
    }
}

@Composable
fun SkyAnimation(
    modifier: Modifier,
    isNight: Boolean
) {
    BoxWithConstraints(
        modifier,
        contentAlignment = Alignment.TopStart
    ) {
        if (isNight) {
            NightStarsAnimationContainer(
                maxWidth.value,
                maxHeight.value
            )
        } else SunAnimationController(
            maxHeight > maxWidth
        )
    }
}

@Composable
fun PrecipitationAnimation(
    modifier: Modifier,
    weather: Weather
) {
    Box(modifier) {
        if (weather.weather == WeatherType.RAIN || weather.weather == WeatherType.DRIZZLE) RainAnimationContainer()
        if (weather.weather == WeatherType.SNOW) SnowAnimationContainer()
    }
}

@Composable
fun WeatherIcon(
    modifier: Modifier,
    weather: Weather,
    isNight: Boolean
) {
    //TODO create a lightning animation and integrate with rain
    if (weather.weather == WeatherType.THUNDERSTORM) {
        Box(
            modifier = modifier
        ) {
            if (isNight) Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    R.drawable.ic_thunder_storm_night
                ),
                contentDescription = weather.weatherDescription
            )
            else Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    R.drawable.ic_thunder_storm_day
                ),
                contentDescription = weather.weatherDescription
            )
        }
    } else {
        Box(
            modifier = modifier
        ) {
            if (isNight) Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    R.drawable.ic_clear_sky_night
                ),
                contentDescription = weather.weatherDescription
            )
            else Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    R.drawable.ic_clear_sky_day
                ),
                contentDescription = weather.weatherDescription
            )
        }
    }
}


private fun WeatherType.isRain(): Boolean = when (this) {
    WeatherType.THUNDERSTORM, WeatherType.DRIZZLE, WeatherType.RAIN -> true
    else -> false
}


/**
 * Catch all for anything which is fog/haze/smoke/mist like
 */
private fun isFogbackground(weather: WeatherType): Boolean {
    return when (weather) {
        WeatherType.CLEAR -> false
        WeatherType.THUNDERSTORM -> false
        WeatherType.DRIZZLE -> false
        WeatherType.RAIN -> false
        WeatherType.SNOW -> false
        WeatherType.CLOUDS -> false
        else -> true
    }
}

private fun WeatherType.getDayWeatherIcon(cloudiness: Int): Int {
    return when (this) {
        WeatherType.CLEAR -> R.drawable.ic_clear_sky_day
        WeatherType.THUNDERSTORM -> R.drawable.ic_thunder_storm_day
        WeatherType.DRIZZLE -> R.drawable.ic_shower_day
        WeatherType.RAIN -> R.drawable.ic_rain_day
        WeatherType.SNOW -> R.drawable.ic_snow_day
        WeatherType.CLOUDS -> {
            when {
                (cloudiness < 51) -> R.drawable.ic_few_clouds_day
                else -> R.drawable.ic_scattered_clouds_day
            }
        }
        else -> R.drawable.ic_mist_day
    }
}

private fun WeatherType.getNightWeatherIcon(cloudiness: Int): Int {
    return when (this) {
        WeatherType.CLEAR -> R.drawable.ic_clear_sky_night
        WeatherType.THUNDERSTORM -> R.drawable.ic_thunder_storm_night
        WeatherType.DRIZZLE -> R.drawable.ic_shower_night
        WeatherType.RAIN -> R.drawable.ic_rain_night
        WeatherType.SNOW -> R.drawable.ic_snow_night
        WeatherType.CLOUDS -> {
            when {
                (cloudiness < 51) -> R.drawable.ic_few_clouds_night
                else -> R.drawable.ic_scattered_clouds_night
            }
        }
        else -> R.drawable.ic_mist_night
    }
}
