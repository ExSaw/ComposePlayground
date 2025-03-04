package com.exsaw.composeplayground.features.advanced_layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme


@Composable
fun IntrinsicsDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Hello world I am some kind..",
                fontSize = 20.sp,
            )
            Checkbox(
                modifier = Modifier
                    .alignByBaseline(),
                checked = true,
                onCheckedChange = null
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Hello world",
                fontSize = 20.sp,
            )
            Checkbox(
                modifier = Modifier
                    .alignByBaseline(),
                checked = true,
                onCheckedChange = null
            )
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
private fun IntrinsicsDemoPreview() {
    ComposePlaygroundTheme {
        IntrinsicsDemo(
            modifier = Modifier
                .systemBarsPadding()
        )
    }
}