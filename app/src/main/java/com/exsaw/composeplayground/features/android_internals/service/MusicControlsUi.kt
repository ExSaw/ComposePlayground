package com.exsaw.composeplayground.features.android_internals.service

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.features.android_internals.service.di.musicServiceModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject


@Composable
fun MusicControlsUiDemo(
    musicController: MusicServiceController,
    modifier: Modifier = Modifier
) {
    val isConnected by musicController.isConnectedState.collectAsStateWithLifecycle()
    val currentSong by musicController.currentSong.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(isConnected) {
            Text(
                text = "Service Connected",
                color = Colors.Bright.Green,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        } else {
            Text(
                text = "Service Disconnected",
                color = Colors.Bright.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = onDebouncedClick { musicController.previous()  }
                ) {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "previous"
                    )
                }
                Text(
                    text = currentSong,
                    modifier = Modifier
                        .weight(1f)
                        .basicMarquee(), // autoScroll
                )
                IconButton(
                    onClick = onDebouncedClick { musicController.next()  }
                ) {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "next"
                    )
                }
                Button(
                    onClick = onDebouncedClick {
                        if(isConnected) {
                            musicController.unbind()
                        } else {
                            musicController.bind()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if(isConnected) {
                            Colors.Bright.Red
                        } else {
                            Colors.Bright.Green
                        }
                    )
                ) {
                    if(isConnected) {
                        Text(text = "Disconnect",)
                    } else {
                        Text(text = "Connect",)
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun MusicControlsUiDemoPreview() {
    val context = LocalContext.current
    KoinApplication(
        application = {
            androidContext(context)
            modules(mainModule, musicServiceModule)
        }
    ) {
        val musicServiceController: MusicServiceController = koinInject()
        ComposePlaygroundTheme {
            MusicControlsUiDemo(musicServiceController)
        }
    }
}