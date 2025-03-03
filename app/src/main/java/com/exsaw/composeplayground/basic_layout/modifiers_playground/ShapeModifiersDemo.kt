package com.exsaw.composeplayground.basic_layout.modifiers_playground

import android.annotation.SuppressLint
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.math.MathUtils
import com.exsaw.composeplayground.R
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication
import java.lang.Math.clamp

@Composable
fun ShapeModifiersDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.music_1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .background(Color.Red)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                    )
                )
        )
        Image(
            painter = painterResource(R.drawable.music_1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .background(Color.Red)
                .clip(
                    MyTriangleShape
                )
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Yellow)
                .drawBehind {
                    val path = Path().apply {
                        moveTo(
                            x = size.width * 0.5f,
                            y = 0f
                        )
                        lineTo(
                            x = 0f,
                            y = size.height
                        )
                        lineTo(
                            x = size.width,
                            y = size.height
                        )
                        close()
                    }
                    drawPath(
                        path = path,
                        color = Color.Green
                    )
                }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = TabButtonShape(
                        activeTab = 1
                    ),
                    ambientColor = Color.Blue,
                    spotColor = Color.Blue,
                )
                .clip(
                    TabButtonShape(
                        activeTab = 1
                    )
                )
                .background(Color.Magenta)
        )
    }
}

class TabButtonShape(
    val activeTab: Int,
) : Shape {

    private val numOfTabs = 3
    private val acrSizePercent = 0.15f

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val activeTabIndex =
            MathUtils.clamp(activeTab, 0, numOfTabs - 1)
        val (w, h) = size.width to size.height
        val tabWidth = w / numOfTabs
        val arcSize = minOf(w, h) * acrSizePercent
        return Outline.Generic(
            when {
                activeTabIndex == 0 -> {
                    Path().apply {
                        moveTo(0f, h)
                        lineTo(0f, arcSize)
                        lineTo(arcSize, arcSize) // arc center
                        lineTo(arcSize, 0f)
                        lineTo(tabWidth - arcSize, 0f)
                        lineTo(tabWidth - arcSize, arcSize)
                        lineTo(tabWidth, arcSize)
                        lineTo(tabWidth, h - arcSize * 2)
                        lineTo(tabWidth + arcSize, h - arcSize * 2)
                        lineTo(tabWidth + arcSize, h - arcSize)
                        lineTo(w - arcSize, h - arcSize)
                        lineTo(w - arcSize, h)
                        addArc(
                            oval = Rect(
                                center = Offset(tabWidth - arcSize, arcSize),
                                radius = arcSize
                            ),
                            startAngleDegrees = 360f,
                            sweepAngleDegrees = 360f
                        )
                        addArc(
                            oval = Rect(
                                center = Offset(arcSize, arcSize),
                                radius = arcSize
                            ),
                            startAngleDegrees = 360f,
                            sweepAngleDegrees = 360f
                        )
                        addArc(
                            oval = Rect(
                                center = Offset(w - arcSize, h),
                                radius = arcSize
                            ),
                            startAngleDegrees = -180f,
                            sweepAngleDegrees = 180f
                        )
                        close()
                    } - Path().apply {
                        moveTo(0f, h)
                        addOval(
                            oval = Rect(
                                center = Offset(tabWidth + arcSize, h - arcSize * 2),
                                radius = arcSize
                            ),
                        )
                    }
                }

                activeTabIndex >= (numOfTabs - 1) -> {
                    Path().apply {
                        moveTo(arcSize, h) // arc center
                        lineTo(arcSize, h - arcSize)
                        lineTo(tabWidth * activeTabIndex - arcSize, h - arcSize)
                        lineTo(tabWidth * activeTabIndex - arcSize, h - arcSize * 2) // oval center
                        lineTo(tabWidth * activeTabIndex, h - arcSize * 2)
                        lineTo(tabWidth * activeTabIndex, arcSize)
                        lineTo(tabWidth * activeTabIndex + arcSize, arcSize) // arc center
                        lineTo(tabWidth * activeTabIndex + arcSize, 0f)
                        lineTo(tabWidth * activeTabIndex, 0f)
                        lineTo(w - arcSize, 0f)
                        lineTo(w - arcSize, arcSize) // arc center
                        lineTo(w, arcSize)
                        lineTo(w, h)
                        addArc(
                            oval = Rect(
                                center = Offset(arcSize, h),
                                radius = arcSize
                            ),
                            startAngleDegrees = -180f,
                            sweepAngleDegrees = 180f
                        )
                        addArc(
                            oval = Rect(
                                center = Offset(tabWidth * activeTabIndex + arcSize, arcSize),
                                radius = arcSize
                            ),
                            startAngleDegrees = -180f,
                            sweepAngleDegrees = 180f
                        )
                        addArc(
                            oval = Rect(
                                center = Offset(w - arcSize, arcSize),
                                radius = arcSize
                            ),
                            startAngleDegrees = -180f,
                            sweepAngleDegrees = 180f
                        )
                        close()
                    } - Path().apply {
                        moveTo(0f, h)
                        addOval(
                            oval = Rect(
                                center = Offset(
                                    tabWidth * activeTabIndex - arcSize,
                                    h - arcSize * 2
                                ),
                                radius = arcSize
                            ),
                        )
                    }
                }

                else -> {
                    Path().apply {
                        moveTo(arcSize, h) // arc center
                        lineTo(arcSize, h - arcSize)
                        lineTo(tabWidth * activeTabIndex - arcSize, h - arcSize)
                        lineTo(tabWidth * activeTabIndex - arcSize, h - arcSize * 2) // oval center
                        lineTo(tabWidth * activeTabIndex, h - arcSize * 2)
                        lineTo(tabWidth * activeTabIndex, arcSize)
                        lineTo(tabWidth * activeTabIndex + arcSize, arcSize) // arc center
                        lineTo(tabWidth * activeTabIndex + arcSize, 0f)
                        lineTo(tabWidth * (activeTabIndex + 1) - arcSize, 0f)
                        lineTo(tabWidth * (activeTabIndex + 1) - arcSize, arcSize) // arc center
                        lineTo(tabWidth * (activeTabIndex + 1), arcSize)
                        lineTo(tabWidth * (activeTabIndex + 1), h - arcSize * 2)
                        lineTo(
                            tabWidth * (activeTabIndex + 1) + arcSize,
                            h - arcSize * 2
                        ) // oval center
                        lineTo(tabWidth * (activeTabIndex + 1) + arcSize, h - arcSize)
                        lineTo(w - arcSize, h - arcSize)
                        lineTo(w - arcSize, h) // arc center
                        addArc(
                            oval = Rect(
                                center = Offset(arcSize, h),
                                radius = arcSize
                            ),
                            startAngleDegrees = -180f,
                            sweepAngleDegrees = 180f
                        )
                        addArc(
                            oval = Rect(
                                center = Offset(tabWidth * activeTabIndex + arcSize, arcSize),
                                radius = arcSize
                            ),
                            startAngleDegrees = -180f,
                            sweepAngleDegrees = 180f
                        )
                        addArc(
                            oval = Rect(
                                center = Offset(tabWidth * (activeTabIndex + 1) - arcSize, arcSize),
                                radius = arcSize
                            ),
                            startAngleDegrees = -180f,
                            sweepAngleDegrees = 180f
                        )
                        addArc(
                            oval = Rect(
                                center = Offset(w - arcSize, h),
                                radius = arcSize
                            ),
                            startAngleDegrees = -180f,
                            sweepAngleDegrees = 180f
                        )
                        close()
                    } - Path().apply {
                        moveTo(0f, h)
                        addOval(
                            oval = Rect(
                                center = Offset(
                                    tabWidth * activeTabIndex - arcSize,
                                    h - arcSize * 2
                                ),
                                radius = arcSize
                            ),
                        )
                    } - Path().apply {
                        moveTo(0f, h)
                        addOval(
                            oval = Rect(
                                center = Offset(
                                    tabWidth * (activeTabIndex + 1) + arcSize,
                                    h - arcSize * 2
                                ),
                                radius = arcSize
                            ),
                        )
                    }
                }
            }
        )
    }
}

data object MyTriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                moveTo(
                    x = size.width * 0.5f,
                    y = 0f
                )
                lineTo(
                    x = 0f,
                    y = size.height
                )
                lineTo(
                    x = size.width,
                    y = size.height
                )
                close()
            }
        )
    }

}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
)
@Composable
fun ShapeModifiersDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            ShapeModifiersDemo()
        }
    }
}