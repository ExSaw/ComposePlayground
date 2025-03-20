package com.exsaw.composeplayground.features.android_internals.service.di

import com.exsaw.composeplayground.di.CoreQualifiers
import com.exsaw.composeplayground.features.android_internals.service.MusicServiceController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val musicServiceModule = module {
    single { MusicServiceController(
        context = androidContext(),
        coroutineScope = get(CoreQualifiers.APP_SCOPE.qualifier),
    ) }
}