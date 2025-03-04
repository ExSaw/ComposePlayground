package com.exsaw.composeplayground.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.features.bottom_nav.BottomNavItemsData
import com.exsaw.composeplayground.features.bottom_nav.CustomBottomNav
import com.exsaw.composeplayground.features.modifiers_playground.MainPageTabsDemo
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun MainPageV2Screen(modifier: Modifier = Modifier) {
    val bottomNavItemsState = remember {
        mutableStateOf(BottomNavItemsData.bottomNavItemsDataDefault)
    } // TODO: itemsHandler
    Scaffold(
        bottomBar = {
            CustomBottomNav(
                itemsState = bottomNavItemsState,
                onClickItem = { clickedItem ->
                    val updatedList = bottomNavItemsState.value.map { item ->
                        item.copy(isSelected = item.index == clickedItem.index)
                    } // TODO: itemsHandler
                    bottomNavItemsState.value = updatedList
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B8AFF)),
        ) {
            MainPageTabsDemo(
                modifier = Modifier
                    .padding(
                        top = 240.dp
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
    device = "id:pixel_4a",
    fontScale = 1.0f,
)
@Composable
fun MainPageV2ScreenPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            MainPageV2Screen()
        }
    }
}