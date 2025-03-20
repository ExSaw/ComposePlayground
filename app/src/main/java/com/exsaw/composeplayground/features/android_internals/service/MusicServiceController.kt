package com.exsaw.composeplayground.features.android_internals.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class MusicServiceController(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
) {
    private lateinit var service: MusicService

    private val _isConnectedState = MutableStateFlow(false)
    val isConnectedState = _isConnectedState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentSong = isConnectedState
        .flatMapLatest { isConnected ->
            if (isConnected) {
                service.currentSong
            } else {
                flowOf("-")
            }
        }.stateIn(
            coroutineScope,
            SharingStarted.Lazily,
            "-"
        )

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicServiceBinder
            this@MusicServiceController.service = binder.getService()
            _isConnectedState.value = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            _isConnectedState.value = false
        }

        override fun onBindingDied(name: ComponentName?) {
            super.onBindingDied(name)
            _isConnectedState.value = false
        }

        override fun onNullBinding(name: ComponentName?) {
            super.onNullBinding(name)
            /*ntd*/
        }
    }

    fun bind() {
        Intent(context, MusicService::class.java).also { intent ->
            intent.putExtra("custom_data", "no_data")
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun unbind() {
        context.unbindService(serviceConnection)
        _isConnectedState.value = false
    }

    fun next() = service.next()

    fun previous() = service.previous()
}