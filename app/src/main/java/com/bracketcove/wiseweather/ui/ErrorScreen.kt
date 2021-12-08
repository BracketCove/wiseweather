package com.bracketcove.wiseweather.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bracketcove.wiseweather.R
import com.bracketcove.wiseweather.ui.currentweather.TitleText
import com.bracketcove.wiseweather.ui.theme.buttonText
import com.bracketcove.wiseweather.ui.theme.headerBold
import com.bracketcove.wiseweather.ui.theme.headerNormal

@Composable
fun ErrorScreen(
    eventHandler: (UiEvent) -> Unit,
    message: String
) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ic_landscape_sunset),
            contentDescription = stringResource(id = R.string.loading_banner),
            contentScale = ContentScale.FillHeight
        )

        TitleText(
            modifier = Modifier.align(
                Alignment.TopCenter
            ).padding(top = 8.dp)
        )

        OutlinedButton(
            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .padding(bottom = 88.dp)
                .width(144.dp)
                .height(48.dp),
            onClick = { eventHandler(UiEvent.Restart) },
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Transparent
            ),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                style = buttonText
            )
        }

        Text(
            text = message,
            style = headerBold.copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .align(
                    Alignment.Center
                ),
            maxLines = 3
        )
    }
}