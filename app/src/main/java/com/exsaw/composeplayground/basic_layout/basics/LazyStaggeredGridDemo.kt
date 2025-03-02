package com.exsaw.composeplayground.basic_layout.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication
import kotlin.random.Random
import kotlin.random.nextInt


@Composable
fun LazyStaggeredGridDemo(modifier: Modifier = Modifier) {
    LazyHorizontalStaggeredGrid(
        modifier = modifier.padding(all = 16.dp),
        rows = StaggeredGridCells.Adaptive(50.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalItemSpacing = 16.dp,
    ) {
        items(100) {
            Box(
                modifier = Modifier
                    .width(Random.nextInt(1..200).dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(Random.nextInt()))
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
fun LazyStaggeredGridDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            LazyStaggeredGridDemo()
        }
    }
}