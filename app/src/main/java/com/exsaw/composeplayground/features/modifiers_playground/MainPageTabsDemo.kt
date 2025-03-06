package com.exsaw.composeplayground.features.modifiers_playground

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.Vibrator
import com.exsaw.composeplayground.tool.debouncedClickable
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun MainPageTabsDemo(modifier: Modifier = Modifier) {
    var activeTab by remember {
        mutableIntStateOf(0)
    }
    val tabsForegroundShape = TabButtonShape(
        activeTab = activeTab
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
                        topStartPercent = 15,
                        topEndPercent = 15,
                    )
                )
                .background(Color(0xE9F0FFFF)),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .shadow(
                    elevation = 5.dp,
                    shape = tabsForegroundShape,
                    ambientColor = Color.Cyan,
                    spotColor = Color.Blue,
                )
                .clip(tabsForegroundShape)
                .background(Color.White),
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                text = "\uD83C\uDFD6\uFE0F Туры",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .debouncedClickable (
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        actionOnLongClick = {
                            logUnlimited("---LONG CLICK")
                        }
                    ) {
                        activeTab = 0
                    }
                    .weight(1f)

            )
            Text(
                text = "\uD83C\uDFE8 Отели",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .debouncedClickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        activeTab = 1
                    }
                    .weight(1f)

            )
            Text(
                text = "\uD83D\uDD25 Горящие",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .debouncedClickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        activeTab = 2
                    }
                    .weight(1f)
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    // backgroundColor = 0xEFE,
    backgroundColor = 0x111,
)
@Composable
fun MainPageTabsDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            MainPageTabsDemo()
        }
    }
}