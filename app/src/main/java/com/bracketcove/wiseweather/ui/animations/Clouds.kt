package com.bracketcove.wiseweather.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bracketcove.wiseweather.R
import kotlin.random.Random

@Composable
fun CloudAnimationContainer(
    windSpeed: Double,
    cloudiness: Int
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart,

        ) {
        when {
            cloudiness < 30 -> CloudAnimationController(windSpeed, maxWidth.value, 0, 350f)
            cloudiness < 60 -> {
                CloudAnimationController(windSpeed, maxWidth.value, 0, 350f)
                CloudAnimationController(windSpeed, maxWidth.value, 500, 400f)
            }
            else -> {
                CloudAnimationController(windSpeed, maxWidth.value, 0, 350f)
                CloudAnimationController(windSpeed, maxWidth.value, 500, 400f)
                CloudAnimationController(windSpeed, maxWidth.value, 1000, 500f)
            }
        }
    }
}

@Composable
fun CloudAnimationController(
    windSpeed: Double,
    maxWidth: Float,
    delay: Int,
    cloudSize: Float
) {
    var yOffset by remember {
        mutableStateOf(
            (Random.nextInt(1, 4) * 100).toFloat().getNextYOffset()
        )
    }

    val yOffsetChanger = {
        yOffset = yOffset.getNextYOffset()
    }

    CloudAnimation(
        calculateCloudAnimSpeed(windSpeed),
        yOffset,
        yOffsetChanger,
        cloudSize,
        maxWidth,
        delay
    )
}

/**
 * wind speed will be variable of 0 to 231 Mp/hr
 * @param windSpeed - Double in either km or miles
 * @param cloudiness - Percentage
 * @param yOffset -
 *
 *
 */
@Composable
fun CloudAnimation(
    animationSpeed: Int,
    yOffset: Float,
    yOffsetChanger: () -> Unit,
    cloudSize: Float,
    screenWidth: Float,
    delay: Int
) {
    val infiniteClouds = rememberInfiniteTransition()

    val movingCloud by infiniteClouds.animateFloat(
        initialValue = -cloudSize,
        targetValue = screenWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(
                animationSpeed,
                easing = LinearEasing,
                delayMillis = delay
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Image(
        painter = painterResource(R.drawable.ic_cloud),
        contentDescription = null,
        modifier = Modifier
            .requiredSize(cloudSize.dp)
            .absoluteOffset(
                x = movingCloud.dp,
                y = yOffset.dp
            )
    )

    if (movingCloud > screenWidth - 1) {
        yOffsetChanger()
    }
}

/**
 * Formula to convert m/h to m/s: divide the value by 2.237
 * wind speed will be variable of 0 to 103.266 m/s
 *
 *  Case: if speed = 0, return anim speed of 8, 000
 *  Case: if speed = approx 100 return anim speed of 1000
 *  Case: if speed = 50 return anim speed of 3500
 *
 */
private fun calculateCloudAnimSpeed(speed: Double): Int {
    return when {
        speed < 10.0 -> 8000
        speed < 20.0 -> 7500
        speed < 30.0 -> 7000
        speed < 40.0 -> 6500
        speed < 50.0 -> 6000
        speed < 60.0 -> 5500
        speed < 70.0 -> 5000
        speed < 80.0 -> 4500
        speed < 90.0 -> 4000
        else -> 3500
    }
}

/**
 * At first I used random values but they seem to be.... not as random as I like.
 */
fun Float.getNextYOffset(): Float {
    return when (this) {
        350f -> 450f
        400f -> 300f
        450f -> 400f
        else -> 350f
    }
}

fun randomCloudSize(): Float =
    Random.nextInt(250, 500).toFloat()