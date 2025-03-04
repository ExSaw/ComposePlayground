package com.exsaw.composeplayground.features.basics

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun ClickableDemo(modifier: Modifier = Modifier) {
    val boxInteractionSource = remember {
        MutableInteractionSource()
    }
    val isBoxPressed by boxInteractionSource.collectIsPressedAsState()
    val isBoxHovered by boxInteractionSource.collectIsHoveredAsState()
    Box(
        modifier = modifier
            .statusBarsPadding()
            .size(100.dp)
            .background(
                if(isBoxPressed) Color.Blue else Color.Yellow
            )
            .clickable(
                enabled = true,
                interactionSource = boxInteractionSource,
                indication = LocalIndication.current,
            ) {

            }
    ) {  }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33
)
@Composable
fun ClickableDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            ClickableDemo()
        }
    }
}