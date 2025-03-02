package com.exsaw.composeplayground.core

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchersProvider {
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
}