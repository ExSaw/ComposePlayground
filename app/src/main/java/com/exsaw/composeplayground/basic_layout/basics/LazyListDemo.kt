package com.exsaw.composeplayground.basic_layout.basics

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyListDemo(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Gray),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(listOf("firstItem", "SecondItem", "ThirdItem")) {
            Text(text = it)
        }
        stickyHeader {
            Text(
                text = "Stick Header 1",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Green)
            )
        }
        items(count = 10) {
            Text("Item $it")
        }
        stickyHeader {
            Text(
                text = "Stick Header 2",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
            )
        }
        items(count = 100) {
            Text("Item $it")
        }
        item {
            Text(
                text = "Last Item",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
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
fun LazyListDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            LazyListDemo()
        }
    }
}