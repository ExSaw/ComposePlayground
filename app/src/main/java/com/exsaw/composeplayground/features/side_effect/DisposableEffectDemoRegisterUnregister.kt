package com.exsaw.composeplayground.features.side_effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication


@Composable
fun DisposableEffectDemo(modifier: Modifier = Modifier) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner.lifecycle) {
        val observer = LifecycleEventObserver { owner, event ->
            when(event) {
                Lifecycle.Event.ON_START -> {
                    logUnlimited("---->DisposableEffectDemo->OnStart")
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
private fun DisposableEffectDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            DisposableEffectDemo()
        }
    }
}