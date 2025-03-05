package com.exsaw.composeplayground.features.advanced_layout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme

@Composable
fun BoxWithConstraintsDemo(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier
            .safeDrawingPadding()
            .wrapContentWidth()
    ) {
        if(constraints.hasFixedWidth) {
            Text(text = "Fixed Width")
        } else {
            Text(text = "Dynamic Width")
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33
)
@Composable
private fun BoxWithConstraintsDemoPreview() {
    ComposePlaygroundTheme {
        BoxWithConstraintsDemo()
    }
}