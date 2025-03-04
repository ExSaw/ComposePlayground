package com.exsaw.composeplayground.features.state_managment.action

sealed interface NumberGuessAction {

    data object OnGuessButtonClick : NumberGuessAction

    data class OnNumberTextChange(val numberText: String) : NumberGuessAction
}