package com.bracketcove.wiseweather.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.bracketcove.wiseweather.R
import com.bracketcove.wiseweather.ui.theme.Sun

private const val LIGHTNING_ANIM_DELAY = 2500

@Composable
fun LightningAnimationContainer(
    maxWidth: Float,
    maxHeight: Float
) {

    val maxSize =  if (maxWidth > maxHeight) maxWidth else maxHeight
    LightningBolt()
    LightningFlash(
        maxSize
    )
}

@Composable
fun LightningBolt() {
    val infiniteTransition = rememberInfiniteTransition()

    val rotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -35f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                200,
                delayMillis = LIGHTNING_ANIM_DELAY
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = painterResource(R.drawable.ic_lightning_bolt),
        contentDescription = null,
        modifier = Modifier
            .requiredSize(128.dp)
            .rotate(rotate)
    )
}

@Composable
fun LightningFlash(
    maxSize: Float,
) {
    val infiniteTransition = rememberInfiniteTransition()

    val lightningAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                200,
                delayMillis = LIGHTNING_ANIM_DELAY
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val lightningSize by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (maxSize * 1.5).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                200,
                delayMillis = LIGHTNING_ANIM_DELAY,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Surface(
        shape = CircleShape,
        modifier = Modifier
            .requiredSize(lightningSize.dp)
            .alpha(
                lightningAlpha
            ),
        color = Sun
    ) {}
}