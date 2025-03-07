package com.exsaw.composeplayground.screen

import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.zIndex
import com.exsaw.composeplayground.R
import com.exsaw.composeplayground.features.bottom_nav.BottomNavItemsData
import com.exsaw.composeplayground.features.bottom_nav.CustomBottomNav
import com.exsaw.composeplayground.features.modifiers_playground.MainPageTabsDemo
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.features.modifiers_playground.MainPageTabItem
import com.exsaw.composeplayground.features.modifiers_playground.TabButtonShape
import com.exsaw.composeplayground.features.modifiers_playground.testTabItems
import com.exsaw.composeplayground.tool.debouncedClickable
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import com.exsaw.composeplayground.util.bottomShadow
import org.koin.compose.KoinApplication
import kotlin.math.roundToInt

@Composable
fun MainPageV2Screen(modifier: Modifier = Modifier) {

    val bottomNavItemsState = remember {
        mutableStateOf(BottomNavItemsData.bottomNavItemsDataDefault)
    } // TODO: itemsHandler
    val mainSearchTabItemsState = remember {
        mutableStateListOf<MainPageTabItem>().apply {
            addAll(testTabItems)
        }
    } // TODO: itemsHandler
    val backgroundColor = remember { mutableStateOf(Color(0xFF0B8AFF)) }
    val isShowHotelClassAndMealsElement = remember {
        mutableStateOf(false)
    }

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
        },
    ) { scaffoldPadding ->
        // status bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(scaffoldPadding.calculateTopPadding())
                .background(backgroundColor.value),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 20.dp
                        )
                        .drawBehind {
                            drawCircle(
                                center = Offset(size.minDimension * 0.5f, 0f),
                                radius = size.maxDimension - 50.dp.toPx(),
                                color = backgroundColor.value,
                            )
                        },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_htkz_image_logo),
                                contentDescription = null
                            )
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_htkz_text_logo),
                                contentDescription = null
                            )
                        }
                        Row(
                            modifier = Modifier
                                .background(
                                    color = Color(0x1A000000),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 8.dp,
                                ),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_speech_ballon),
                                modifier = Modifier.size(20.dp),
                                contentDescription = null,
                            )
                            Text(
                                text = "Чат с менеджером",
                                color = Color.White,
                                fontSize = 12.sp,
                            )
                        }
                    }
                    MainPageTabsDemo(
                        modifier = Modifier
                            .zIndex(1f),
                        tabItems = mainSearchTabItemsState,
                        onClickTabItem = { clickedItem ->
                            mainSearchTabItemsState.replaceAll { item ->
                                item.copy(
                                    isActive = clickedItem.index == item.index
                                )
                            }
                            backgroundColor.value = when (clickedItem.index) {
                                0 -> Color(0xFF0B8AFF)
                                1 -> Color(0xFF0054A1)
                                else -> Color(0xff57B059)
                            }
                            logUnlimited("--->MainPageV2Screen->onClickTabItem->$clickedItem")
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(
                                    bottomStartPercent = 5,
                                    bottomEndPercent = 5
                                ),
                                ambientColor = Color(0xFF000000),
                                spotColor = Color(0x650073FF),
                            )
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(
                                    bottomStartPercent = 5,
                                    bottomEndPercent = 5
                                )
                            )
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        MainPageToursSearchContent(
                            isShowHotelClassAndMealsElement = isShowHotelClassAndMealsElement,
                            onClickExpand = {
                                isShowHotelClassAndMealsElement.value = true
                            }
                        )
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .background(
                            color = Color(0xff57B059),
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Colors.Mono.White)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Colors.Mono.White)
                )
            }
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
fun MainPageV2ScreenPortraitPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            MainPageV2Screen()
        }
    }
}