package com.exsaw.composeplayground.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.debouncedClickable
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import com.exsaw.composeplayground.ui.theme.Typography
import org.koin.compose.KoinApplication

@Composable
fun MainPageToursSearchContent(
    isShowHotelClassAndMealsElement: State<Boolean>,
    onClickExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = "Откуда",
                color = Colors.Mono.Gray_50,
                fontSize = 12.sp,
            )
            Text(
                text = "Алматы",
                color = Colors.Mono.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .background(Color(0x1A000000))
        )
        Column {
            Text(
                text = "Куда",
                color = Colors.Mono.Gray_50,
                fontSize = 12.sp,
            )
            Text(
                text = "Турция",
                color = Colors.Mono.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .background(Color(0x1A000000))
        )
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Когда",
                    color = Colors.Mono.Gray_50,
                    fontSize = 12.sp,
                )
                Text(
                    text = "27 апреля",
                    color = Colors.Mono.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            VerticalDivider(
                modifier = Modifier
                    .background(Color(0x1A000000))
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "На сколько",
                    color = Colors.Mono.Gray_50,
                    fontSize = 12.sp,
                )
                Text(
                    text = "7-8 ночей",
                    color = Colors.Mono.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .background(Color(0x1A000000))
        )
        Column {
            Text(
                text = "Кто поедед",
                color = Colors.Mono.Gray_50,
                fontSize = 12.sp,
            )
            Text(
                text = "2 взрослых",
                color = Colors.Mono.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .background(Color(0x1A000000))
        )
        if (!isShowHotelClassAndMealsElement.value) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .debouncedClickable {
                        onClickExpand()
                    },
                text = "Класс отеля и питание",
                color = Color(0xFF1B5AE7),
                fontSize = 16.sp,
            )
        }
        if (isShowHotelClassAndMealsElement.value) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Класс отеля",
                        color = Colors.Mono.Gray_50,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "5,4,3",
                        color = Colors.Mono.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                VerticalDivider(
                    modifier = Modifier
                        .background(Color(0x1A000000))
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Тип питания",
                        color = Colors.Mono.Gray_50,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "Все включено",
                        color = Colors.Mono.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color(0xFFFDD600),
                    shape = RoundedCornerShape(
                        16.dp
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Найти",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun MainPageToursSearchContentPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            MainPageToursSearchContent(
                isShowHotelClassAndMealsElement = mutableStateOf(true),
                onClickExpand = {}
            )
        }
    }
}