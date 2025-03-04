package com.exsaw.composeplayground.features.bottom_nav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.R
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import com.exsaw.composeplayground.util.nonScaledSp
import org.koin.compose.KoinApplication


@Composable
fun BottomNavDemo1(modifier: Modifier = Modifier) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Box(
        modifier = modifier,
    ) {
        NavigationBar(
            containerColor = Color.White,
            modifier = modifier
                .height(65.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavItemsData.bottomNavItemsDataDefault.forEach { item ->
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            this@NavigationBar.NavigationBarItem(
                                selected = selectedItemIndex == item.index,
                                onClick = onDebouncedClick(rememberCoroutineScope()) {
                                    selectedItemIndex = item.index
                                    // navController.navigate(...)
                                },
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            when {
                                                item.badgeCount != null -> {
                                                    Badge {
                                                        Text(
                                                            text = item.badgeCount.toString(),
                                                            fontSize = 10.nonScaledSp,
                                                            lineHeight = 10.nonScaledSp,
                                                        )
                                                    }
                                                }

                                                item.hasNews -> {
                                                    Badge()
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(item.icon),
                                            contentDescription = item.title
                                        )
                                    }
                                },
                                enabled = true,
                                label = {
                                    Text(
                                        text = item.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Visible,
                                        fontSize = 10.nonScaledSp,
                                        lineHeight = 12.nonScaledSp,
                                    )
                                },
                                alwaysShowLabel = true,
                                colors = NavigationBarItemColors(
                                    selectedIconColor = colorResource(R.color.blue_1),
                                    selectedTextColor = colorResource(R.color.blue_1),
                                    selectedIndicatorColor = Color.Unspecified,
                                    unselectedIconColor = colorResource(R.color.gray_1),
                                    unselectedTextColor = colorResource(R.color.gray_1),
                                    disabledIconColor = colorResource(R.color.gray_1),
                                    disabledTextColor = colorResource(R.color.gray_1)
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

//    Scaffold(
//        bottomBar = {
//
//        }
//    ) { paddingValues ->
//        Text(
//            modifier = Modifier
//                .padding(paddingValues)
//                .width(10.dp),
//            text = "12121",
//            fontSize = 12.nonScaledSp,
//            lineHeight = 12.nonScaledSp,
//        )
//    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    apiLevel = 33,
    fontScale = 2.0f,
)
@Composable
fun BottomNavDemo1Preview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            BottomNavDemo1()
        }
    }
}