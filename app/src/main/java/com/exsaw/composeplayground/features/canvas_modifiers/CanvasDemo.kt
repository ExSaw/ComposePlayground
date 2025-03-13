package com.exsaw.composeplayground.features.canvas_modifiers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun CanvasModifiersDemo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Colors.Pale.Yellow)
            .drawWithContent {
                withTransform(
                    transformBlock = {
                        this@withTransform.rotate(45f)
                    },
                    drawBlock = {
                        drawRect(
                            brush = Brush.linearGradient(
                                listOf(
                                    Colors.Mono.Black,
                                    Colors.Pale.Red,
                                    Colors.Bright.Red
                                )
                            ),
                        )
                    }
                )
                drawContent() // controls
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hello World"
        )
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
private fun CompositionLocalDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            CanvasModifiersDemo()
        }
    }
}