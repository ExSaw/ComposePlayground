package com.exsaw.composeplayground.features.modifiers_playground

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun ModifierOrderDemo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Red)
            .padding(16.dp)
            .clip(CircleShape)
            .background(Color.Green),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "hello world",
            textAlign = TextAlign.Center
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
)
@Composable
fun NumberGuessPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            ModifierOrderDemo()
        }
    }
}