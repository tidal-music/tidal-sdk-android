package com.tidal.sdk.player.streamingprivileges

import android.os.Handler

internal class ReleaseRunnable(
    private val mutableState: MutableState,
    private val networkInteractionsHandler: Handler,
) : Runnable {

    override fun run() {
        mutableState.keepAlive = false
        networkInteractionsHandler.looper.quitSafely()
    }
}
