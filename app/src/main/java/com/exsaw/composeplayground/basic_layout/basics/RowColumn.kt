package com.exsaw.composeplayground.basic_layout.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme

@Composable
fun RowColumnDemo(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
//        Text(
//            text = "Hello World",
//            fontSize = 44.sp,
//            modifier = Modifier
//                .width(150.dp)
//                .alignBy(LastBaseline),
//        )
//        Text(
//            text = "Hello World",
//            fontSize = 24.sp,
//            modifier = Modifier.alignByBaseline(),
//        )
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(Color.Red)
//                .weight(2f)
//        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Green)
                .weight(1f)
        )
        Column(
            modifier = Modifier.weight(3f)
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.Blue)
            )
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.Red)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
)
@Composable
fun RowColumnDemoPreview(modifier: Modifier = Modifier) {
    ComposePlaygroundTheme {
        RowColumnDemo()
    }
}