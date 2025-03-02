package com.exsaw.composeplayground.basic_layout.state_managment.di

import com.exsaw.composeplayground.basic_layout.state_managment.viewmodel.NumberGuessViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val numberGuessScreenModule = module {
    viewModel {
        NumberGuessViewModel(
            dispatchers = get()
        )
    }
}