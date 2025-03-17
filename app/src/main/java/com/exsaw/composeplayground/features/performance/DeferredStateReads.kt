package com.exsaw.composeplayground.features.performance

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.core.math.MathUtils
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication


@Composable
fun DeferredStateReadsDemo(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val fabBtnOffset: State<Dp> = remember {
        derivedStateOf {
            val percentage = (1f - (listState.firstVisibleItemIndex) * 0.1f)
            (percentage * 100.dp).coerceIn(
                minimumValue = 0.dp,
                maximumValue = 100.dp
            )
        }
    }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animColor by infiniteTransition.animateColor(
        initialValue = Colors.Opaque.Red,
        targetValue = Colors.Opaque.Blue,
        label = "color anim",
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse,
        )
    )
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            MovingFloatingActionButton(
                color = { animColor },
                offset = { fabBtnOffset.value },
                onClick = onDebouncedClick {
                    launch(it.default) {
                        listState.animateScrollToItem(0)
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(50) {
                Text(
                    text = "Item $it",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }
        }
    }
}

@Composable
private fun MovingFloatingActionButton(
    color: () -> Color,
    offset: () -> Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .offset {
                IntOffset(x = 0, y = offset().roundToPx()) // correct !
            }
            .drawWithContent {
                drawContent()
                drawRect(
                    color = color()
                )
            }
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Scroll to top",
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun DeferredStateReadsDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            DeferredStateReadsDemo(
                modifier = Modifier
                    .safeDrawingPadding()
            )
        }
    }
}