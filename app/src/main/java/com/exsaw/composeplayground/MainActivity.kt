package com.exsaw.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.exsaw.composeplayground.features.animation.AnimLayoutLookaheadDemo
import com.exsaw.composeplayground.features.animation.AnimVisibilityDemo
import com.exsaw.composeplayground.features.internals.StabilityDemo
import com.exsaw.composeplayground.features.performance.BitmapCompressor
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val bitmapCompressor: BitmapCompressor by inject()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePlaygroundTheme {

                val statusBarLight = Color.White
                val statusBarDark = Color.Black
                val navigationBarLight = Color.White
                val navigationBarDark = Color.Black
                val isDarkMode = isSystemInDarkTheme()

                DisposableEffect(isDarkMode) {
                    this@MainActivity.enableEdgeToEdge(
                        statusBarStyle = if (!isDarkMode) {
                            SystemBarStyle.light(
                                statusBarLight.toArgb(),
                                statusBarDark.toArgb()
                            )
                        } else {
                            SystemBarStyle.dark(
                                statusBarDark.toArgb()
                            )
                        },
                        navigationBarStyle = if (!isDarkMode) {
                            SystemBarStyle.light(
                                navigationBarLight.toArgb(),
                                navigationBarDark.toArgb()
                            )
                        } else {
                            SystemBarStyle.dark(navigationBarDark.toArgb())
                        }
                    )

                    onDispose { }
                }

                //              MainPageV2Screen()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimLayoutLookaheadDemo(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }

            }
        }
    }
}