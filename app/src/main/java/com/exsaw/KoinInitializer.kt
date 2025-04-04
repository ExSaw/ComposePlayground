package com.exsaw

/**
 * @author A.Chudov
 */
import android.content.Context
import androidx.startup.Initializer
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.features.android_internals.service.di.musicServiceModule
import com.exsaw.composeplayground.features.state_managment.di.numberGuessScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin

/**
 * Special class for Jetpack startup library to accelerate loading process
 * I'v moved Koin initialization from application class to jetpack startup initializer for
 * app's startup acceleration
 * Inspect AndroidManifest for more details. This class is included in providers section.
 */
class KoinInitializer : Initializer<KoinApplication> {

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

    override fun create(context: Context): KoinApplication =
        startKoin {
            androidLogger()
            allowOverride(false)
            androidContext(context)
            modules(mainModule, numberGuessScreenModule, musicServiceModule)
        }
}