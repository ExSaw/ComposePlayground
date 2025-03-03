package com.exsaw.composeplayground.basic_layout.basics

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableModsDemo(modifier: Modifier = Modifier) {
    val redDragOffset = remember {
        mutableStateOf(Offset.Zero)
    }
    val greenDragOffset = remember {
        mutableStateOf(Offset.Zero)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset {
                    redDragOffset.value.round()
                }
                .draggable2D(
                    state = rememberDraggable2DState { offset -> // xy pos
                        redDragOffset.value += offset
                    },
                )
                .clip(CircleShape)
                .background(Color.Red),
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset {
                    greenDragOffset.value.round()
                }
           //     .anchoredDraggable()  // to study
                .draggable(
                    state = rememberDraggableState { offset -> // xy pos
                        greenDragOffset.value += Offset(
                            x = 0f,
                            y = offset
                        )
                    },
                    orientation = Orientation.Vertical,
                )
                .clip(CircleShape)
                .background(Color.Green),
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33
)
@Composable
fun DraggableModsDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            DraggableModsDemo()
        }
    }
}