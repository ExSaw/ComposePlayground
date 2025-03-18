package com.exsaw.composeplayground.features.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun AnimVisibilityDemo(modifier: Modifier = Modifier) {
    val isVisibleState = remember {
        mutableStateOf(true)
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Button(
            onClick = onDebouncedClick {
                isVisibleState.value = !isVisibleState.value
            }
        ) {
            Text("Toggle visibility")
        }
        val appearAnimSpecs: FiniteAnimationSpec<Float> =
            tween(
                durationMillis = 1000,
                delayMillis = 300,
                easing = LinearEasing
            )
        val disappearAnimSpecs: FiniteAnimationSpec<IntSize> =
            spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMedium,
                visibilityThreshold = null
            )
        AnimatedVisibility(
            visible = isVisibleState.value,
            enter = scaleIn(
                keyframes {
                    durationMillis = 5000
                    0.0f at 2500 using LinearEasing
                }
            ) + fadeIn(appearAnimSpecs),
            exit = shrinkOut(disappearAnimSpecs),
        ) {
            Text(
                text = "Hello World",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .border(
                        width = 5.dp,
                        color = Colors.Bright.Red,
                    )
                    .drawBehind {
                        drawRect(
                            color = Colors.Mono.Black.copy(alpha = 0.1f)
                        )
                    }
                    .wrapContentSize()
            )
        }
        Text("Static Text")
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = false,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun AnimVisibilityDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            AnimVisibilityDemo()
        }
    }
}