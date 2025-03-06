package com.exsaw.composeplayground.tool

import androidx.compose.ui.Modifier


fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier = if (condition) this.then(modifier()) else this