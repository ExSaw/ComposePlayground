package com.exsaw.composeplayground.features.basics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun StateHoistingDemo(modifier: Modifier = Modifier) {
    val counter = rememberSaveable {
        mutableIntStateOf(0)
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CounterDemo(
            counter = counter,
            onCounterButtonClick = {
                counter.intValue++
            },
            modifier = Modifier
        )
        Button(
            onClick = {
                counter.intValue = 0
            }
        ) {
            Text(text = "Reset counter")
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
    device = "id:pixel_4a",
    fontScale = 2.0f,
)
@Composable
fun StateHoistingDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            StateHoistingDemo()
        }
    }
}