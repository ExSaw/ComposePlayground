package com.exsaw.composeplayground.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.exsaw.composeplayground.tool.logUnlimited

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

fun Modifier.printConstraints(tag: String): Modifier {
    return layout { measurable, constraints ->
        logUnlimited("---->printConstraints->tag=$tag->$constraints")
        val placeable = measurable.measure(
            constraints = constraints
        )
        layout(
            width = placeable.width,
            height = placeable.height
        ) {
            placeable.place(0, 0)
        }
    }
}