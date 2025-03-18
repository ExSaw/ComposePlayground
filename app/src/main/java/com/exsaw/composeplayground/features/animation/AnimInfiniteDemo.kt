package com.exsaw.composeplayground.features.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun AnimInfiniteDemo(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(
        label = "infinite transition",
    )
    val ratio by transition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 3000),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(
                offsetMillis = 1000,
                offsetType = StartOffsetType.FastForward
            )
        ),
        label = "ratio animation"
    )
    val color by transition.animateColor(
        initialValue = Colors.Bright.Purple,
        targetValue = Colors.Bright.Orange,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 3000),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(
                offsetMillis = 1000,
                offsetType = StartOffsetType.FastForward
            )
        ),
        label = "color animation"
    )
    Box(
        modifier = modifier
            .graphicsLayer {
                rotationZ = ratio * 360f
                scaleX = ratio
                scaleY = ratio
            }
            .size(100.dp)
            .drawBehind {
                drawRect(
                    color = color,
                )
            }
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.TopStart)
        )
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.TopEnd)
        )
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomStart)
        )
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
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
private fun AnimInfiniteDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            AnimInfiniteDemo()
        }
    }
}