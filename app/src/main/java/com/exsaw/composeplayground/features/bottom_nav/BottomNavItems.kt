package com.exsaw.composeplayground.features.bottom_nav

import androidx.annotation.DrawableRes
import com.exsaw.composeplayground.R

object BottomNavItemsData {
    data class BottomNavItem(
        val isSelected: Boolean,
        val index: Int,
        val title: String,
        @DrawableRes val icon: Int,
        val hasNews: Boolean,
        val badgeCount: Int? = null,
    )

    val bottomNavItemsDataDefault = listOf(
        BottomNavItem(
            isSelected = true,
            index = 0,
            title = "Поиск",
            icon = R.drawable.ic_bottom_nav_search_black,
            hasNews = false,
        ),
        BottomNavItem(
            isSelected = false,
            index = 1,
            title = "Сохраненное",
            icon = R.drawable.ic_bottom_nav_favorites_black,
            hasNews = false,
        ),
        BottomNavItem(
            isSelected = false,
            index = 2,
            title = "Мой тур",
            icon = R.drawable.ic_bottom_nav_my_tour_black,
            hasNews = false,
        ),
        BottomNavItem(
            isSelected = false,
            index = 3,
            title = "Уведомления",
            icon = R.drawable.ic_bottom_nav_notifications_black,
            hasNews = true,
        ),
        BottomNavItem(
            isSelected = false,
            index = 4,
            title = "Настройки",
            icon = R.drawable.ic_bottom_nav_settings_black,
            hasNews = false,
            badgeCount = 25
        ),
    )
}