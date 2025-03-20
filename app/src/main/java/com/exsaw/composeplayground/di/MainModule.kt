package com.exsaw.composeplayground.di

import com.exsaw.composeplayground.core.IDispatchersProvider
import com.exsaw.composeplayground.core.StandardDispatchers
import com.exsaw.composeplayground.features.performance.BitmapCompressor
import com.exsaw.composeplayground.tool.Vibrator
import com.exsaw.composeplayground.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single(CoreQualifiers.SCOPE_FOR_WRITE_OPERATIONS.qualifier) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineName(CoreQualifiers.SCOPE_FOR_WRITE_OPERATIONS.name))
    }
    single(CoreQualifiers.APP_SCOPE.qualifier) {
        CoroutineScope(Dispatchers.Default + CoroutineName(CoreQualifiers.APP_SCOPE.name))
    }
    singleOf<IDispatchersProvider>(::StandardDispatchers)
    singleOf(::Vibrator)
    single {
        BitmapCompressor(
            context = androidContext(),
            dispatchers = get()
        )
    }
    viewModel { MainViewModel(get()) }
}