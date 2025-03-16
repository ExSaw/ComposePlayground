package com.exsaw.composeplayground.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.exsaw.composeplayground.R

val HTFontFamily = FontFamily(
    Font(
        resId = R.font.sans_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.sans_semibold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.sans_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.sans_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.sans_light,
        weight = FontWeight.Light
    ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = HTFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = HTFontFamily,
    ),
    displayMedium = TextStyle(
        fontFamily = HTFontFamily,
    ),
    displaySmall = TextStyle(
        fontFamily = HTFontFamily,
    ),
    headlineLarge = TextStyle(
        fontFamily = HTFontFamily,
    ),
    headlineMedium = TextStyle(
        fontFamily = HTFontFamily,
    ),
    headlineSmall = TextStyle(
        fontFamily = HTFontFamily,
    ),
    titleLarge = TextStyle(
        fontFamily = HTFontFamily,
    ),
    titleMedium = TextStyle(
        fontFamily = HTFontFamily,
    ),
    titleSmall = TextStyle(
        fontFamily = HTFontFamily,
    ),
    bodyMedium = TextStyle(
        fontFamily = HTFontFamily,
    ),
    bodySmall = TextStyle(
        fontFamily = HTFontFamily,
    ),
    labelLarge = TextStyle(
        fontFamily = HTFontFamily,
    ),
    labelMedium = TextStyle(
        fontFamily = HTFontFamily,
    ),
    labelSmall = TextStyle(
        fontFamily = HTFontFamily,
    ),
)