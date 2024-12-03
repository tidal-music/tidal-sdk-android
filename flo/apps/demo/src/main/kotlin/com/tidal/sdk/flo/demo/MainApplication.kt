package com.tidal.sdk.flo.demo

import android.app.Application
import android.net.ConnectivityManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.tidal.sdk.flo.core.FloClient

internal class MainApplication : Application() {

    val demoFloClient by lazy {
        FloClient(
            getSystemService(ConnectivityManager::class.java),
            { "atoken" },
            { false },
            TODO("URL to your Flo backend here"),
            HandlerThread("DemoFloClient::Operations")
                .also {
                    it.start()
                }.looper.run {
                    Handler(this)
                },
            Handler(Looper.getMainLooper()),
        )
    }
}
