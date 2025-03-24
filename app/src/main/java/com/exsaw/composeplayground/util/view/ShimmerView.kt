package com.exsaw.composeplayground.util.view

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exsaw.composeplayground.tool.applyIf
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.util.px
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun LoadingPlaceholder(modifier: Modifier) {
    var a by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(true) {
        while (true) {
            delay(2000L)
            a++
        }
    }
    Column(modifier) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .background(Colors.Pale.Blue, RoundedCornerShape(36.dp))
                .then(
                    if (a % 2 == 0) {
                        Modifier.shimmerEffect()
                    } else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello World", fontSize = 24.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .background(Colors.Pale.Blue, RoundedCornerShape(36.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (a % 2 != 0) {
                Text("Hello World", fontSize = 24.sp)
            } else {
                ShimmeringCompose(
                    modifier = Modifier
                        .matchParentSize(),
                    cornerRadius = 36.dp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .background(Colors.Pale.Blue, RoundedCornerShape(36.dp))
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .background(Colors.Mono.Gray_20, RoundedCornerShape(36.dp))
                .applyIf(
                    condition = a % 2 == 0,
                ) {
                    background(Colors.Bright.Green)
                }
        )

        ShimmerEffectV2(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .background(Color.LightGray, RoundedCornerShape(36.dp))
        )

        Surface(
            shape = RoundedCornerShape(36.dp),
            color = Colors.Pale.Blue,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerLoading(isVisible = { a%2 == 0}),
                contentAlignment = Alignment.Center,
            ) {
                Text("Surface")
            }
        }
    }
}

@Composable
fun ShimmerEffectV2(
    modifier: Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
) {
    val shimmerColors = listOf(
        Color.White.copy(alpha = 0.3f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 1.0f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 0.3f),
    )
    val transition = rememberInfiniteTransition(
        label = "infinite transition"
    )
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer animation",
    )
    Box(
        modifier = modifier
    ) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .drawBehind {
                    drawRect(
                        Brush.linearGradient(
                            colors = shimmerColors,
                            start = Offset(x = translateAnimation - widthOfShadowBrush, y = 0.0f),
                            end = Offset(x = translateAnimation, y = angleOfAxisY),
                        )
                    )
                }
        )
    }
}

@Composable
fun ShimmerEffect(
    modifier: Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
) {
    val shimmerColors = listOf(
        Color.White.copy(alpha = 0.3f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 1.0f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 0.3f),
    )
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer animation",
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
    )
    Box(
        modifier = modifier
    ) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(brush)
        )
    }
}

// GOOD
@Composable
fun Modifier.shimmerLoading(
    isVisible: () -> Boolean,
    shimmerColor: Color = Color.White,
    backgroundColor: Color = Color(0xFFEAEAEA),
    duration: Duration = 1.seconds,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
): Modifier {
    if(!isVisible()) return this
    val shimmerColors = listOf(
        shimmerColor.copy(alpha = 0.35f),
        shimmerColor.copy(alpha = 0.7f),
        shimmerColor.copy(alpha = 1f),
        shimmerColor.copy(alpha = 0.7f),
        shimmerColor.copy(alpha = 0.35f),
    )
    val transition = rememberInfiniteTransition(
        label = "infinite transition"
    )
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (duration.inWholeMilliseconds + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration.inWholeMilliseconds.toInt(),
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer animation",
    )
    return drawBehind {
        drawRect(backgroundColor)
        drawRect(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            )
        )
    }
}

// PL
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0x40B8B5B5),
                Color(0x80FFFFFF),
                Color(0xFFA100FF),
                Color(0x80FFFFFF),
                Color(0x40B0B0B0),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

@Composable
fun ShimmeringCompose(
    modifier: Modifier,
    cornerRadius: Dp
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val translateAnim by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = screenWidth.px,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        )
    )

    val shimmerColorShades = listOf(Color(0xFFF37F19), Color(0xFF007CFF), Color(0xFFF37F19))

    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 70.dp.px, translateAnim + 35.dp.px)
    )

    Box(modifier = modifier.background(brush = brush, shape = RoundedCornerShape(cornerRadius)))
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
private fun ShimmerViewPreview() {
    LoadingPlaceholder(Modifier)
}