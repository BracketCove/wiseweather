package com.bracketcove.wiseweather.ui.theme

import android.content.Context
import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.bracketcove.wiseweather.R

val Sun = Color(0xFFf2c512)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

private val Ultra = FontFamily(
    Font(R.font.ultra_regular, FontWeight.Normal)
)

/**
 * Figured it was best to send in the Typeface to avoid a reference to context
 */
val headerBold = TextStyle(
    color = Color.White.copy(alpha = .92f),
    fontFamily = Ultra,
    fontWeight = FontWeight.Normal,
    fontSize = 41.sp,
    shadow = Shadow(
        color = Color.Black,
        offset = Offset(4f, 4f),
        blurRadius = 8f
    )
)

val headerNormal = TextStyle(
    color = Color.White.copy(alpha = .92f),
    fontFamily = FontFamily.Serif,
    fontWeight = FontWeight.Normal,
    fontSize = 41.sp,
    shadow = Shadow(
        color = Color.Black,
        offset = Offset(4f, 4f),
        blurRadius = 8f
    )
)

val buttonText = TextStyle(
    color = Color.White,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontSize = 21.sp
)

