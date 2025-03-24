package com.exsaw.composeplayground.features.navigation_multibackstack

import com.exsaw.composeplayground.core.IDispatchersProvider
import com.exsaw.composeplayground.features.bottom_nav.BottomNavItemsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BottomNavItemsHandler(
    private val dispatchers: IDispatchersProvider
) {
    private val _bottomNavItemsState =
        MutableStateFlow(BottomNavItemsData.bottomNavItemsDataDefault)
    val bottomNavItemsState = _bottomNavItemsState.asStateFlow()

    fun onClickItem(clickedItem: BottomNavItemsData.BottomNavItem) {
        _bottomNavItemsState.update {
            it.map { item ->
                item.copy(isSelected = item.index == clickedItem.index)
            }
        }
    }
}