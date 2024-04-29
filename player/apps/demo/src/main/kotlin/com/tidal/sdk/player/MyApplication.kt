package com.tidal.sdk.player

import android.app.Application
import android.util.Log
import com.instacart.library.truetime.TrueTime

internal class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Thread(
            {
                while (true) {
                    try {
                        TrueTime()
                            .withRootDelayMax(Float.MAX_VALUE)
                            .withRootDispersionMax(Float.MAX_VALUE)
                            .withServerResponseDelayMax(Int.MAX_VALUE)
                            .initialize()
                        break
                    } catch (@Suppress("TooGenericExceptionCaught") e: Throwable) {
                        Log.e(this::class.java.simpleName, "TrueTime initialization error", e)
                        Thread.sleep(1)
                    }
                }
            },
            "TrueTime initialization",
        ).start()
    }
}
