package com.exsaw.composeplayground.features.modifiers_playground

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.constraintlayout.compose.Dimension
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.debouncedClickable
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication
import kotlin.time.Duration.Companion.seconds

@Composable
fun MainPageTabsDemo(
    modifier: Modifier = Modifier,
    tabItems: List<MainPageTabItem>,
    onClickTabItem: (MainPageTabItem) -> Unit,
) {
    val activeTabIndex = tabItems.indexOfFirst { it.isActive }
        .takeIf { it != -1 } ?: 0

    val tabsForegroundShape = TabButtonShape(
        activeTabIndex = activeTabIndex,
        tabsCount = tabItems.size
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStartPercent = 25,
                        topEndPercent = 25,
                    )
                )
                .background(Color(0xFFE9F0FF)),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()

                .clip(
                    GenericShape { size, _ ->
                        moveTo(-50f, -50f)
                        lineTo(-50f, size.height)
                        lineTo(size.width + 50f, size.height)
                        lineTo(size.width + 50f, -50f)
                        lineTo(size.width + 50f, -50f)
                        close()
                    }
                )
                .shadow(
                    elevation = 8.dp,
                    shape = tabsForegroundShape,
                    ambientColor = Color(0xFF000000),
                    spotColor = Color(0x650073FF),
                )
                .background(Color.White),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            tabItems.forEachIndexed { index, item ->
                Text(
                    text = item.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .debouncedClickable {
                            onClickTabItem(item)
                        }
                        .weight(1f)
                )
                val dividerAlpha = if (!item.isActive
                    && index != tabItems.lastIndex
                    && (index + 1 <= tabItems.lastIndex
                            && !tabItems[index + 1].isActive)
                ) 1.0f else 0.0f
                VerticalDivider(
                    modifier = Modifier
                        .alpha(dividerAlpha)
                        .background(Color(0x1A000000))
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    backgroundColor = 0x111,
)
@Composable
fun MainPageTabsDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        val tabItems by remember {
            mutableStateOf(testTabItems)
        }
        ComposePlaygroundTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .background(Colors.Mono.Gray_10)
            ) {
                MainPageTabsDemo(
                    modifier = modifier,
                    tabItems = tabItems,
                    onClickTabItem = {}
                )
            }
        }
    }
}

val testTabItems = listOf(
    MainPageTabItem(
        index = 0,
        title = "\uD83C\uDFD6\uFE0F Туры",
        isActive = true
    ),
    MainPageTabItem(
        index = 1,
        title = "\uD83C\uDFE8 Отели",
        isActive = false
    ),
    MainPageTabItem(
        index = 2,
        title = "\uD83D\uDD25 Горящие",
        isActive = false
    ),
)

data class MainPageTabItem(
    val index: Int,
    val title: String,
    val isActive: Boolean
)