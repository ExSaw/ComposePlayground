package com.exsaw.composeplayground.util

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Updater
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.exsaw.composeplayground.tool.logUnlimited
import org.koin.compose.viewmodel.koinViewModel

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

fun Modifier.bottomShadow(shadow: Dp) =
    this
        .clip(GenericShape { size, _ ->
            lineTo(size.width, 0f)
            lineTo(size.width, Float.MAX_VALUE)
            lineTo(0f, Float.MAX_VALUE)
        })
        .shadow(shadow)


@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.koinNavSharedViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}

inline fun <T> SavedStateHandle.update(key: String, update: (T) -> T) {
    synchronized(this) {
        val current = get<T>(key)
        val updated = update(current ?: return)
        set(key, updated)
    }
}