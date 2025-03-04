package com.exsaw.composeplayground.features.advanced_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.util.printConstraints

@Composable
fun SizeModsDemo(modifier: Modifier = Modifier) {
    // first constraints - [0, screenW, 0, screenH]
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth() // [screenW, screenW, 0, screenH]
            .background(Color.Red),
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .printConstraints("Box1 before 1. fillMaxWidth")
                .fillMaxWidth(
                    fraction = 0.5f // screenW * 0.5f
                )
                .printConstraints("Box1 after 1. fillMaxWidth")
                .background(Color.Yellow),
        )
        Box(
            modifier = Modifier
                .height(100.dp)
                .printConstraints("Box2 before 2. fillMaxWidth")
                .fillMaxWidth(
                    fraction = 0.5f // screenW * 0.5f * 0.5f
                )
                .printConstraints("Box2 after 2. fillMaxWidth")
                .background(Color.Green),
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = false,
    apiLevel = 33
)
@Composable
private fun SizeModsDemoPreview() {
    SizeModsDemo()
}