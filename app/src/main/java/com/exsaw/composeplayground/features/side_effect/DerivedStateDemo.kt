package com.exsaw.composeplayground.features.side_effect

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication


@Composable
fun DerivedStateDemo(modifier: Modifier = Modifier) {

    val lazyListState = rememberLazyListState()
    val isShowScrollToTopButton by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex >= 10
        }
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        items(50) {
            Text(
                text = "Item $it",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }

    val scope = rememberCoroutineScope()

    if(isShowScrollToTopButton) {
        FloatingActionButton(
            onClick =  {
                scope.launch {
                    lazyListState.animateScrollToItem(0)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(end = 16.dp, bottom = 16.dp)
                .wrapContentSize(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Scroll Up"
            )
        }
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
private fun DerivedStateDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            DerivedStateDemo()
        }
    }
}