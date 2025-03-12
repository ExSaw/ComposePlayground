package com.exsaw.composeplayground.features.side_effect

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.delay
import org.koin.compose.KoinApplication
import kotlin.time.Duration.Companion.seconds

@Composable
fun ProduceStateDemo(modifier: Modifier = Modifier) {
    // variant 1
//    val counter = remember {
//        mutableIntStateOf(0)
//    }
//    LaunchedEffect(Unit) {
//        while (true) {
//            delay(1.seconds)
//            counter.intValue++
//        }
//    }

    // variant 2
    val counter by produceState(initialValue = 0) {
        while (true) {
            delay(1.seconds)
            value += 1
        }
    }

    Text(
        text = "$counter",
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
    )
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
private fun ProduceStateDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            ProduceStateDemo()
        }
    }
}