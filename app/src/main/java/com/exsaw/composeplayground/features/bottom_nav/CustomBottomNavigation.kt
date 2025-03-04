package com.exsaw.composeplayground.features.bottom_nav

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.R
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.debouncedClickable
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import com.exsaw.composeplayground.util.nonScaledSp
import org.koin.compose.KoinApplication


@Composable
fun CustomBottomNav(
    modifier: Modifier = Modifier,
    itemsState: MutableState<List<BottomNavItemsData.BottomNavItem>>,
    onClickItem: (BottomNavItemsData.BottomNavItem) -> Unit,
) {
    Box(
        modifier
            .shadow(
                elevation = 12.dp,
                ambientColor = Color.Black,
                spotColor = Color.Black,
            )
            .background(Color.White),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .fillMaxWidth()
                .height(65.dp)
                .padding(top = 10.dp),
        ) {
            itemsState.value.forEach { item ->
                BottomNavButton(
                    item = item,
                    modifier = Modifier
                        .weight(1f),
                    onClick = onClickItem
                )
            }
        }
    }
}

@Composable
fun BottomNavButton(
    item: BottomNavItemsData.BottomNavItem,
    modifier: Modifier = Modifier,
    onClick: (BottomNavItemsData.BottomNavItem) -> Unit,
) {
    Column(
        modifier = modifier
            .debouncedClickable { onClick(item) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val itemColor: Color =
            when (item.isSelected) {
                true -> colorResource(R.color.blue_1)
                false -> colorResource(R.color.gray_1)
            }
        Box {
            Image(
                modifier = Modifier
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(item.icon),
                contentDescription = item.title,
                colorFilter = ColorFilter.tint(itemColor),
            )
            if (item.hasNews) {
                Box(
                    modifier = Modifier
                        .offset(x = 16.dp)
                        .size(8.dp)
                        .background(
                            color = Color.Red,
                            shape = CircleShape,
                        )
                )
            }

        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = item.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 10.nonScaledSp,
            lineHeight = 12.nonScaledSp,
            letterSpacing = 1.nonScaledSp,
            color = itemColor
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
    device = "id:pixel_4",
    fontScale = 100.0f,
)
@Composable
fun MainScreenPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
            ) {
                CustomBottomNav(
                    itemsState = mutableStateOf(
                        BottomNavItemsData.bottomNavItemsDataDefault
                    ),
                    onClickItem = {}
                )
            }
        }
    }
}

//@Preview(
//    showBackground = true,
//    backgroundColor = 0xEFE,
//    showSystemUi = true,
//    apiLevel = 33,
//    device = Devices.NEXUS_10,
//    fontScale = 2.0f,
//)
//@Composable
//fun MainScreenPreviewXL(modifier: Modifier = Modifier) {
//    KoinApplication(
//        application = { modules(mainModule) }
//    ) {
//        ComposePlaygroundTheme {
//            MainScreen()
//        }
//    }
//}