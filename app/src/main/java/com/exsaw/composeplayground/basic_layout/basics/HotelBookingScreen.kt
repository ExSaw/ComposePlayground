package com.exsaw.composeplayground.basic_layout.basics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import com.exsaw.composeplayground.R
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

private val tagsData = listOf(
    "City Center",
    "Luxury",
    "Instant Booking",
    "Exclusive Deals",
    "Early Bird Discount",
    "Romantic Gateway",
    "24/7 Support",
)
private val hotelDescription = "The advertisement features a vibrant and inviting design, showcasing the Hotel California Strawberry nestled in the heart of Los Angeles. Surrounded by the iconic Hollywood Sign, Griffith Park, and stunning beaches, the hotel is perfectly located for guests to explore L.A.’s best attractions."
private val hotelAdditionalTitle = "What we offer"
private val hotelOffersData = mapOf(
    R.drawable.bed to "2 Bed",
    R.drawable.breakfast to "Breakfast",
    R.drawable.cutlery to "Cutlery",
    R.drawable.pawprint to "Pet friendly",
    R.drawable.serving_dish to "Dinner",
    R.drawable.snowflake to "Air Conditioner",
    R.drawable.television to "TV",
    R.drawable.wi_fi_icon to "Wifi",
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HotelBookingScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Box {
                Image(
                    painter = painterResource(R.drawable.living_room),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(
                            max = 250.dp,

                            ),
                    contentScale = ContentScale.Crop,
                )
                HotelFadeBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                )
            }
        }
        item {
            HorizontalDivider(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
        }
        item {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                tagsData.forEach { tagData ->
                    AssistChip(
                        label = {
                            Text(text = tagData)
                        },
                        onClick = onDebouncedClick(rememberCoroutineScope()) {
                            logUnlimited("---->HotelBookingScreen->ClickedTag=$tagData")
                        }
                    )
                }
            }
        }
        item {
            HorizontalDivider(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
        }
        item {
            Text(
                text = hotelDescription.trimIndent(),
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Justify,
            )
        }
        item {
            Text(
                text = hotelAdditionalTitle.trimIndent(),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                items(
                    items = hotelOffersData.entries.toList()
                ) { (drawableResId, labelText) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                drawableResId
                            ),
                            contentDescription = labelText,
                            modifier = Modifier
                                .size(36.dp)
                        )
                        Text(
                            text = labelText,
                            fontSize = 13.sp,
                        )
                    }
                }
            }
        }
        item {
            Button(
                onClick = onDebouncedClick(rememberCoroutineScope()) {
                    logUnlimited("---->HotelBookingScreen->Book now")
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .widthIn(
                        max = 400.dp
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Book Now!"
                )
            }
        }
    }
}

@Composable
fun HotelFadeBanner(
    modifier: Modifier = Modifier
) {
    val windowClass = currentWindowAdaptiveInfo().windowSizeClass
    Row(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.7f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(Color(0x1A0088FF))
                .weight(1f),
        ) {
            Text(
                text = "Hotel California Strawberry Strawberry Strawberry Strawberry Strawberry",
                fontWeight = FontWeight.Bold,
                fontSize = when (windowClass.windowWidthSizeClass) {
                    WindowWidthSizeClass.COMPACT -> 18.sp
                    WindowWidthSizeClass.MEDIUM -> 24.sp
                    WindowWidthSizeClass.EXPANDED -> 28.sp
                    else -> 18.sp
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            LabeledIcon(
                text = "Los Angeles, California",
                icon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        tint = Color.Gray,
                        contentDescription = null
                    )
                },
            )
            LabeledIcon(
                text = "4.9 (13K Reviews)",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        tint = Color.Yellow,
                        contentDescription = null
                    )
                },
            )
        }
        Text(
            text = buildAnnotatedString {
                val fontSizeMultiplier = when (windowClass.windowWidthSizeClass) {
                    WindowWidthSizeClass.COMPACT -> 1f
                    WindowWidthSizeClass.MEDIUM -> 1.2f
                    WindowWidthSizeClass.EXPANDED -> 1.5f
                    else -> 1f
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp * fontSizeMultiplier
                    )
                ) {
                    append("420$/")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp * fontSizeMultiplier
                    )
                ) {
                    append("night")
                }
            }
        )
    }
}

@Composable
fun LabeledIcon(
    text: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Text(
            text = text,
            fontSize = 13.sp,
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
)
fun HotelBookingScreenPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            HotelBookingScreen()
        }
    }
}