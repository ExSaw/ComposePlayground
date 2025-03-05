package com.exsaw.composeplayground.features.advanced_layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMapIndexedNotNull
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlin.math.roundToInt

val defaultListOfLazyLayout2dMapItems = listOf(
    LazyLayout2dMapItem(
        title = "Item 1",
        percentageOffset = Offset(0f,0f)
    ),
    LazyLayout2dMapItem(
        title = "Item 2",
        percentageOffset = Offset(1f,0f)
    ),
    LazyLayout2dMapItem(
        title = "Item 3",
        percentageOffset = Offset(0.3f,-0.5f)
    ),
    LazyLayout2dMapItem(
        title = "Item 4",
        percentageOffset = Offset(-0.2f,1.5f)
    )
)

data class LazyLayout2dMapItem(
    val title: String,
    val percentageOffset: Offset,
)

data class ProcessedLazyLayout2dMapItem(
    val placeable: Placeable,
    val finalXPos: Int,
    val finalYPos: Int,
)

data class LazyLayout2dMapItemSizeBounds(
    val minWidth: Dp = 50.dp,
    val maxWidth: Dp = 150.dp,
    val minHeight: Dp = 50.dp,
    val maxHeight: Dp = 75.dp,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyLayout2dMapDemo(
    items: List<LazyLayout2dMapItem>,
    itemSizeBounds: LazyLayout2dMapItemSizeBounds = LazyLayout2dMapItemSizeBounds(),
    mapOffset: IntOffset = IntOffset.Zero,
    onDrag: (delta: IntOffset) -> Unit,
    itemModifier: Modifier = Modifier,
    modifier: Modifier = Modifier
) {
    LazyLayout(
        modifier = modifier
            .draggable2D(
                state = rememberDraggable2DState { offset ->
                    onDrag(offset.round())
                }
            ),
        itemProvider = {
            object : LazyLayoutItemProvider {
                override val itemCount: Int = items.size

                @Composable
                override fun Item(index: Int, key: Any) {
                    // P1 - here could be more children, so a placeable is an array
                    Text(
                        text = items[index].title,
                        textAlign = TextAlign.Center,
                        color = Colors.Mono.Gray_50,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = itemModifier
                            .widthIn(
                                min = itemSizeBounds.minWidth,
                                max = itemSizeBounds.maxWidth
                            )
                            .heightIn(
                                min = itemSizeBounds.minHeight,
                                max = itemSizeBounds.maxHeight
                            )
                            .border(
                                width = 2.dp,
                                color = Colors.Mono.Gray_50
                            )
                            .padding(16.dp)
                    )
                }
            }
        },
    ) { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val visibleArea = IntRect(
            left = 0,
            top = 0,
            right = layoutWidth,
            bottom = layoutHeight
        )
        val visibleItems = items.fastMapIndexedNotNull { index, item ->
            val finalXPos =
                (item.percentageOffset.x * layoutWidth + layoutWidth * 0.5f + mapOffset.x)
                    .roundToInt()
            val finalYPos =
                (item.percentageOffset.y * layoutHeight + layoutHeight * 0.5f + mapOffset.y)
                    .roundToInt()
            val maxItemWidth = itemSizeBounds.maxWidth.roundToPx()
            val maxItemHeight = itemSizeBounds.maxHeight.roundToPx()
            val extendedItemBounds = IntRect( // invisible box around to place items far away from each other
                left = (finalXPos - maxItemWidth * 0.5f).roundToInt(),
                top = (finalYPos - maxItemHeight * 0.5f).roundToInt(),
                right = (finalXPos + 3f * (maxItemWidth * 0.5f)).roundToInt(),
                bottom = (finalYPos + 3f * (maxItemHeight * 0.5f)).roundToInt()
            )
            if(visibleArea.overlaps(extendedItemBounds)) {
                val placeable = measure(
                    index = index,
                    constraints = Constraints()
                ).first() // see P1 - it's an array
                ProcessedLazyLayout2dMapItem(
                    placeable = placeable,
                    finalXPos = finalXPos,
                    finalYPos = finalYPos
                )
            } else {
                null
            }
        }
        
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ){
            visibleItems.fastForEach { item ->
                item.placeable.place(
                    x = item.finalXPos,
                    y = item.finalYPos,
                )
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
private fun LazyLayout2dMapDemoPreview() {
    ComposePlaygroundTheme {
        LazyScrollingDemo(
            modifier = Modifier
                .background(color = Colors.Pale.Green)
        )
    }
}