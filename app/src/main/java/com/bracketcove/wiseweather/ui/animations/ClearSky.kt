package com.bracketcove.wiseweather.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bracketcove.wiseweather.R
import com.bracketcove.wiseweather.ui.currentweather.ICON_SIZE

@Composable
fun NightStarsAnimationContainer(
    maxWidth: Float,
    maxHeight: Float
) {
    /*
    These values represent different positions on the screen
     */
    val wStart = maxWidth * 0.15
    val wQuarter = maxWidth * 0.25
    val wHalf = maxWidth * 0.50
    val wThreeQuarter = maxWidth * 0.75
    val wEnd = maxWidth * 0.85

    val hStart = maxHeight * 0.15
    val hQuarter = maxHeight * 0.25
    val hHalf = maxHeight * 0.50
    val hThreeQuarter = maxHeight * 0.75
    val hEnd = maxHeight * 0.85

    NightStarAnimationController(xOffset = wHalf.dp, yOffset = hThreeQuarter.dp, delay = 1000)
    NightStarAnimationController(xOffset = wQuarter.dp, yOffset = hEnd.dp, delay = 2000)
    NightStarAnimationController(xOffset = wQuarter.dp, yOffset = hHalf.dp, delay = 3000)
    NightStarAnimationController(xOffset = wThreeQuarter.dp, yOffset = hQuarter.dp, delay = 4000)
    NightStarAnimationController(xOffset = wHalf.dp, yOffset = hStart.dp, delay = 5000)
    NightStarAnimationController(xOffset = wEnd.dp, yOffset = hHalf.dp, delay = 2500)
    NightStarAnimationController(xOffset = wQuarter.dp, yOffset = hQuarter.dp, delay = 1500)
    NightStarAnimationController(xOffset = wStart.dp, yOffset = hThreeQuarter.dp, delay = 1500)
    NightStarAnimationController(
        xOffset = wThreeQuarter.dp,
        yOffset = hThreeQuarter.dp,
        delay = 3500
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_moon),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )
    }
}

@Composable
fun NightStarAnimationController(xOffset: Dp, yOffset: Dp, delay: Int) {
    Box(
        Modifier
            .wrapContentSize()
            .absoluteOffset(
                x = xOffset,
                y = yOffset
            )
    ) {
        NightStarAnimation(delay = delay)

    }
}

@Composable
fun NightStarAnimation(
    delay: Int
) {
    val shiningStar = rememberInfiniteTransition()


    val starAlpha by shiningStar.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                500,
                delayMillis = delay
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = painterResource(R.drawable.ic_star),
        contentDescription = null,
        modifier = Modifier
            .requiredSize(16.dp)
            .alpha(
                starAlpha
            )
    )
}


@Composable
fun SunAnimationController(
    isPortrait: Boolean
) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = if (isPortrait) Alignment.BottomCenter else Alignment.Center
    ) {
        SunAnimation(delay = 1000, angle = 30f, ICON_SIZE.dp)
        SunAnimation(delay = 0, angle = 0f, ICON_SIZE.dp)
    }
}

@Composable
fun SunAnimation(
    delay: Int,
    angle: Float,
    size: Dp
) {
    val sparklingSun = rememberInfiniteTransition()

    val sunAlpha by sparklingSun.animateFloat(
        initialValue = 1f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                1000,
                easing = LinearEasing,
                delayMillis = delay
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Image(
        painter = painterResource(R.drawable.ic_sun),
        contentDescription = null,
        modifier = Modifier
            .requiredSize(size)
            .rotate(angle)
            .alpha(
                sunAlpha
            )
    )
}