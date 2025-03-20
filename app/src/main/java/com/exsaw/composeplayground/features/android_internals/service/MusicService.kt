package com.exsaw.composeplayground.features.android_internals.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.exsaw.composeplayground.core.IDispatchersProvider
import com.exsaw.composeplayground.tool.logUnlimited
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.ext.android.inject

class MusicService: Service() {

    private val dispatchers: IDispatchersProvider by inject()
    private val binder = MusicServiceBinder()
    private val serviceScope = CoroutineScope(dispatchers.main + SupervisorJob())

    private val _currentSongIndex = MutableStateFlow(0)
    val currentSong = _currentSongIndex
        .map { songs[it] }
        .stateIn(
            serviceScope,
            SharingStarted.Lazily,
            songs[_currentSongIndex.value]
        )

    fun previous() {
        if(_currentSongIndex.value == 0) {
            _currentSongIndex.value = songs.lastIndex
        } else {
            _currentSongIndex.value--
        }
    }

    fun next() {
        if(_currentSongIndex.value == songs.lastIndex) {
            _currentSongIndex.value = 0
        } else {
            _currentSongIndex.value++
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val startData = intent?.getStringExtra("custom_data")
        logUnlimited("---MusicService->onStartCommand->$startData")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        /*ntd*/
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class MusicServiceBinder: Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.coroutineContext.cancelChildren()
    }
}

private val songs = listOf(
    "Blinding Lights – The Weeknd",
    "Shape of You – Ed Sheeran",
    "Bohemian Rhapsody – Queen",
    "Levitating – Dua Lipa",
    "Smells Like Teen Spirit – Nirvana",
    "Rolling in the Deep – Adele",
    "Uptown Funk – Mark Ronson ft. Bruno Mars",
    "Bad Guy – Billie Eilish",
    "Hotel California – Eagles",
    "As It Was – Harry Styles"
)