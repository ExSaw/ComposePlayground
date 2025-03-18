package com.exsaw.composeplayground.features.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun AnimTransitionDemo(modifier: Modifier = Modifier) {
    val isVisibleState = remember {
        mutableStateOf(true)
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Button(
            onClick = onDebouncedClick {
                isVisibleState.value = !isVisibleState.value
            }
        ) {
            Text("Toggle visibility")
        }
        AnimatedContent(
            targetState = isVisibleState.value,
            transitionSpec = {
                (slideIntoContainer(
                    towards = if(isVisibleState.value) {
                        AnimatedContentTransitionScope.SlideDirection.Right
                    } else {
                        AnimatedContentTransitionScope.SlideDirection.Left
                    },
                    animationSpec = tween(3000)
                )) togetherWith slideOutOfContainer(
                    towards = if(isVisibleState.value) {
                        AnimatedContentTransitionScope.SlideDirection.Right
                    } else {
                        AnimatedContentTransitionScope.SlideDirection.Left
                    },
                    animationSpec = tween(3000)
                )
            }
        ) { isVisible ->
            if (isVisible) {
                Text(
                    text = "Hello World Red Box",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                        .border(
                            width = 5.dp,
                            color = Colors.Bright.Red,
                        )
                        .drawBehind {
                            drawRect(
                                color = Colors.Mono.Black.copy(alpha = 0.1f)
                            )
                        }
                        .wrapContentSize()
                )
            } else {
                Text(
                    text = "Hello World Blue Box",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                        .border(
                            width = 5.dp,
                            color = Colors.Bright.Blue,
                        )
                        .drawBehind {
                            drawRect(
                                color = Colors.Mono.Black.copy(alpha = 0.1f)
                            )
                        }
                        .wrapContentSize()
                )
            }
        }
        Text("Static Text")
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = false,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun AnimTransitionDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            AnimTransitionDemo()
        }
    }
}