package com.exsaw.composeplayground.features.advanced_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMaxOfOrNull
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun CustomLayoutPagedRowNotOptimizedDemo(
    page: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables: List<Measurable>, constraints: Constraints ->

        // measure phase

        val layoutConstraints = constraints.copy() // just in case

        // measurable - any box, row, text etc... inside content
        // constraints - passed by parent -> how much space available
        // measure all children -> measure itself -> place all
        val placeable = measurables.map {
            it.measure(
                constraints.copy(
                    minWidth = 0,
                    minHeight = 0
                )
            )
        }

        val pages = mutableListOf<List<Placeable>>()
        var currentPage = mutableListOf<Placeable>()
        var currentPageWidth = 0

        placeable.fastForEach { placeable ->
            if(currentPageWidth + placeable.width > constraints.maxWidth) {
                pages.add(currentPage)
                currentPage = mutableListOf()
                currentPageWidth = 0
            }
            currentPage.add(placeable)
            currentPageWidth += placeable.width
        }

        if(currentPage.isNotEmpty()) {
            pages.add(currentPage)
        }

        val pageItems = pages.getOrNull(page) ?: emptyList()
        val maxHeight = pageItems.fastMaxOfOrNull{ it.height } ?: 0

        layout( // place phase
            width = constraints.maxWidth,
            height = maxHeight,
        ) {
            var xOffset = 0
            pageItems.fastForEach { placeable ->
                placeable.place(xOffset, 0)
                xOffset += placeable.width
            }
        }
    }
}

@Composable
fun SubComposePageRowDemo(
    page: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier,
    ) {constraints: Constraints ->

        val pages = mutableListOf<List<Placeable>>()
        var currentPage = mutableListOf<Placeable>()
        var currentPageWidth = 0

        val measurables = subcompose( // special func of SubcomposeLayout
            slotId = "content",
            content = content
        )

        for(measurable in measurables) {

            val placeable = measurable.measure(constraints)

            if(currentPageWidth + placeable.width > constraints.maxWidth) {
                if(pages.size == page) {
                    break // don't need to measure more
                }
                pages.add(currentPage)
                currentPage = mutableListOf()
                currentPageWidth = 0
            }
            currentPage.add(placeable)
            currentPageWidth += placeable.width
        }

        if(currentPage.isNotEmpty()) {
            pages.add(currentPage)
        }

        val pageItems = pages.getOrNull(page) ?: emptyList()
        val maxHeight = pageItems.fastMaxOfOrNull{ it.height } ?: 0

        layout(
            width = constraints.maxWidth,
            height = maxHeight,
        ) {
            var xOffset = 0
            pageItems.fastForEach { placeable ->
                placeable.place(xOffset, 0)
                xOffset += placeable.width
            }
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
private fun CustomLayoutPagedRowDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            SubComposePageRowDemo(
                page = 0,
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(100.dp)
                        .background(Colors.Bright.Green)
                )
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(150.dp)
                        .background(Colors.Bright.Blue)
                )
                Box(
                    modifier = Modifier
                        .width(75.dp)
                        .height(100.dp)
                        .background(Colors.Bright.Yellow)
                )
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(100.dp)
                        .background(Colors.Bright.Purple)
                )
            }
        }
    }
}