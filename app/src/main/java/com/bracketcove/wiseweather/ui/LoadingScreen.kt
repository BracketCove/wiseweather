package com.bracketcove.wiseweather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bracketcove.wiseweather.R
import com.bracketcove.wiseweather.ui.theme.headerBold

@Composable
fun LoadingScreen() {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ic_landscape_day),
            contentDescription = stringResource(id = R.string.loading_banner),
            contentScale = ContentScale.FillHeight
        )

        LinearProgressIndicator(
            Modifier.align(Alignment.TopCenter).padding(top = 128.dp),
            color = Color.White,
            backgroundColor = Color.Gray
        )

        Text(
            text = stringResource(id = R.string.loading),
            style = headerBold,
            modifier = Modifier.align(
                Alignment.TopCenter
            ).padding(top = 64.dp),
        )
    }
}