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
fun SnowAnimationContainer() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        SnowAnimation(maxHeight.value, maxWidth.value * 0.1f, 0)
        SnowAnimation(maxHeight.value, maxWidth.value * 0.21f, 300)
        SnowAnimation(maxHeight.value, maxWidth.value * 0.33f, 600)
        SnowAnimation(maxHeight.value, maxWidth.value * 0.45f, 1200)
        SnowAnimation(maxHeight.value, maxWidth.value * 0.53f, 450)
        SnowAnimation(maxHeight.value, maxWidth.value * 0.67f, 900)
        SnowAnimation(maxHeight.value, maxWidth.value * 0.77f, 1400)
        SnowAnimation(maxHeight.value, maxWidth.value * 0.84f, 200)
    }
}

@Composable
fun SnowAnimation(
    screenHeight: Float,
    xOffset: Float,
    delay: Int
) {
    val infinite = rememberInfiniteTransition()

    val xOffsetChange by infinite.animateFloat(
        initialValue = xOffset,
        targetValue = xOffset + 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                1000,
                easing = LinearEasing,
                delayMillis = delay
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val snowDrop by infinite.animateFloat(
        initialValue = 0f - 40f,
        targetValue = screenHeight,
        animationSpec = infiniteRepeatable(
            animation = tween(
                5000,
                easing = LinearEasing,
                delayMillis = delay
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Image(
        painter = painterResource(R.drawable.ic_snowflake),
        contentDescription = null,
        modifier = Modifier
            .requiredSize(36.dp)
            .absoluteOffset(
                x = xOffsetChange.dp,
                y = snowDrop.dp
            )
    )
}