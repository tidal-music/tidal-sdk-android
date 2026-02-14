package com.simple.musicplayer

import android.app.Application
import android.util.Log
import com.instacart.library.truetime.TrueTime

/**
 * Application class that initializes required libraries
 */
class MusicPlayerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize TrueTime (required by TIDAL SDK for accurate time syncing)
        Thread {
            try {
                TrueTime()
                    .withRootDelayMax(Float.MAX_VALUE)
                    .withRootDispersionMax(Float.MAX_VALUE)
                    .withServerResponseDelayMax(Int.MAX_VALUE)
                    .initialize()
                Log.d("MusicPlayerApp", "TrueTime initialized successfully")
            } catch (e: Exception) {
                Log.e("MusicPlayerApp", "TrueTime initialization failed", e)
            }
        }.start()
    }
}
