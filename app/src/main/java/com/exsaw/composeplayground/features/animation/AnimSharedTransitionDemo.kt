package com.exsaw.composeplayground.features.animation

import android.os.VibrationEffect.Composition
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exsaw.composeplayground.R
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.debouncedClickable
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

// https://pl-coding.mymemberspot.de/library/aUvl7Unv6eye8zuJA75P/QQVzzw4UZIEh9dej4qv2/4rjhNe03B6Gh6EvmV54f/details
@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope =
    staticCompositionLocalOf<SharedTransitionScope?> { null } // bad

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AnimSharedTransitionDemo(modifier: Modifier = Modifier) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    SharedTransitionLayout(
        modifier = modifier
    ) {// sharedTransScope

        CompositionLocalProvider(
            LocalSharedTransitionScope.provides(this) // no really need
        ) {
            // place ui here for using LocalSharedTransitionScope
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                this@Column.AnimatedVisibility(
                    visible = !isExpanded
                ) {
                    RowListItem(
                        onClick = {
                            isExpanded = !isExpanded
                        },
                        animatedVisibilityScope = this,
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = "item-layout"
                                ),
                                animatedVisibilityScope = this,
                            ),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                this@Column.AnimatedVisibility(
                    visible = isExpanded
                ) {
                    ColumnListItem(
                        onClick = {
                            isExpanded = !isExpanded
                        },
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = "item-layout"
                                ),
                                animatedVisibilityScope = this,
                            ),
                        animatedVisibilityScope = this,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.RowListItem(
    onClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(16.dp)
            .debouncedClickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.android),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "image"),
                    animatedVisibilityScope = animatedVisibilityScope,
                ),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "title"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
                text = "List Item",
                fontSize = 20.sp,
            )
            Text(
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "description"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
                text = "This is the list item description",
                fontSize = 14.sp,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.ColumnListItem(
    onClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .debouncedClickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.android),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "image"),
                    animatedVisibilityScope = animatedVisibilityScope,
                ),
        )
        Text(
            modifier = Modifier
                .sharedElement(
                    state = rememberSharedContentState(key = "title"),
                    animatedVisibilityScope = animatedVisibilityScope,
                ),
            text = "List Item",
            fontSize = 20.sp,
        )
        Text(
            modifier = Modifier
                .sharedElement(
                    state = rememberSharedContentState(key = "description"),
                    animatedVisibilityScope = animatedVisibilityScope,
                ),
            text = "This is the list item description",
            fontSize = 14.sp,
        )
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
private fun AnimSharedTransitionDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            AnimSharedTransitionDemo()
        }
    }
}