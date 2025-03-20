package com.exsaw.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.exsaw.composeplayground.features.android_internals.custom_viewmodel.CustomViewModelProvider
import com.exsaw.composeplayground.features.android_internals.custom_viewmodel.CustomViewModelStore
import com.exsaw.composeplayground.features.android_internals.custom_viewmodel.MainCustomViewModel
import com.exsaw.composeplayground.features.android_internals.service.MusicControlsUiDemo
import com.exsaw.composeplayground.features.android_internals.service.MusicServiceController
import com.exsaw.composeplayground.features.performance.BitmapCompressor
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.serialization.Serializable
import org.koin.android.ext.android.inject

@Serializable
data object ScreenA

@Serializable
data object ScreenB

@Serializable
data class ScreenC(val greeting: String)

@Serializable
data object ScreenBAndCGraph

class MainActivity : ComponentActivity() {

    private val bitmapCompressor: BitmapCompressor by inject()
    private val musicServiceController: MusicServiceController by inject()

  //  private val viewModel: MainViewModel by viewModel()

    private lateinit var customViewModelStore: CustomViewModelStore
    private lateinit var customViewModelProvider: CustomViewModelProvider
    private lateinit var mainCustomViewModel: MainCustomViewModel

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lastStore = lastCustomNonConfigurationInstance as? CustomViewModelStore
        customViewModelStore = lastStore ?: CustomViewModelStore()
        customViewModelProvider = CustomViewModelProvider(
            store = customViewModelStore,
            factory = MainCustomViewModel.Factory(
                initialCounter = 5
            )
        )
        mainCustomViewModel = customViewModelProvider.get(MainCustomViewModel::class.java)

        enableEdgeToEdge()

//        ComposeView(this).apply {
//            setContent {}
//        }
        setContent {
//            Box {
//                AndroidView(
//                    factory = {
//                        TextView(it)
//                    }
//                )
//            }
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

                // MainPageV2Screen()

                // simple NAVIGATION
                // AndroidInternalsCounterRoot()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MusicControlsUiDemo(
                        musicController = musicServiceController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }


            }  // end
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return customViewModelStore
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isFinishing) {
            customViewModelStore.clear()
        }
    }
}