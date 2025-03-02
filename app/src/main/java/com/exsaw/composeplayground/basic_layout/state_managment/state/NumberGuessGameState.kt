package com.exsaw.composeplayground.basic_layout.state_managment.state


data class NumberGuessGameState(
    val secretNumber: Int,
    val attempts: Int = 0,
    val enteredNumber: Int? = null,
    val isGuessCorrect: Boolean = false,
)