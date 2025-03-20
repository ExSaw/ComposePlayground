package com.exsaw.composeplayground.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.util.update
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        logUnlimited("---MainViewModel->INIT->${this.hashCode()}")
    }

    val counterState = savedStateHandle.getStateFlow("counter", 0)

    val greeting = savedStateHandle.get<String>("greeting")

    init {
        logUnlimited("---MainViewModel->greeting=$greeting") // arguments of ScreenC
    }

    fun increaseCounter() {
        savedStateHandle.update<Int>("counter") { it + 1 }
    }
}