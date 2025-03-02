package com.exsaw.composeplayground.basic_layout.state_managment.state


data class NumberGuessUIState(
    val numberText: String = "",
    val guessText: String,
    val guessButtonText: String,
    val isGuessCorrect: Boolean = false,
)