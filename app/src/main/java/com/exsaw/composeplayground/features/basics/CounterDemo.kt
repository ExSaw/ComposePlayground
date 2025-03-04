package com.exsaw.composeplayground.features.basics

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication
import kotlin.random.Random


@Composable
fun CounterDemo(
    counter: MutableIntState,
    onCounterButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val items = rememberSaveable {
            mutableStateOf(listOf<String>("Item"))
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onCounterButtonClick
        ) {
            Text(
                text = "Count=${counter.intValue}"
            )
            Text(
                text = " rand=${Random.nextInt()}"
            )
        }
        Text(
            text = " rand=${Random.nextInt()}"
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
    device = "id:pixel_4a",
    fontScale = 2.0f,
)
@Composable
fun CounterDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            CounterDemo(
                counter = mutableIntStateOf(0),
                onCounterButtonClick = {}
            )
        }
    }
}