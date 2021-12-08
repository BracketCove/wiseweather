package com.bracketcove.wiseweather.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bracketcove.wiseweather.R

@Composable
fun RainAnimationContainer() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        RainAnimation(maxHeight.value, maxWidth.value * 0.1f, 0)
        RainAnimation(maxHeight.value, maxWidth.value * 0.16f, 100)
        RainAnimation(maxHeight.value, maxWidth.value * 0.21f, 50)
        RainAnimation(maxHeight.value, maxWidth.value * 0.27f, 125)
        RainAnimation(maxHeight.value, maxWidth.value * 0.33f, 200)
        RainAnimation(maxHeight.value, maxWidth.value * 0.45f, 100)
        RainAnimation(maxHeight.value, maxWidth.value * 0.53f, 150)
        RainAnimation(maxHeight.value, maxWidth.value * 0.67f, 75)
        RainAnimation(maxHeight.value, maxWidth.value * 0.72f, 0)
        RainAnimation(maxHeight.value, maxWidth.value * 0.77f, 25)
        RainAnimation(maxHeight.value, maxWidth.value * 0.84f, 175)
        RainAnimation(maxHeight.value, maxWidth.value * 0.91f, 150)
    }
}

@Composable
fun RainAnimation(
    screenHeight: Float,
    xOffset: Float,
    delay: Int
) {
    val infiniteRain = rememberInfiniteTransition()

    val rainDrop by infiniteRain.animateFloat(
        initialValue = 0f - 36f,
        targetValue = screenHeight,
        animationSpec = infiniteRepeatable(
            animation = tween(
                800,
                easing = LinearEasing,
                delayMillis = delay
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Image(
        painter = painterResource(R.drawable.ic_drop_155933),
        contentDescription = null,
        modifier = Modifier
            .requiredSize(36.dp)
            .absoluteOffset(
                x = xOffset.dp,
                y = rainDrop.dp
            )
    )
}