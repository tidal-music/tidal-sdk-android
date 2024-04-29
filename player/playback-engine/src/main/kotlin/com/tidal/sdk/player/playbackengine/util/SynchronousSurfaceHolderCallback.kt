package com.tidal.sdk.player.playbackengine.util

import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

internal class SynchronousSurfaceHolderCallback @AssistedInject constructor(
    private val handler: Handler,
    @Assisted private val delegate: SurfaceHolder.Callback,
) : SurfaceHolder.Callback {

    private val reentrantLock = ReentrantLock(true)

    override fun surfaceCreated(holder: SurfaceHolder) = dispatchAndWaitFor {
        delegate.surfaceCreated(holder)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) =
        dispatchAndWaitFor {
            delegate.surfaceChanged(holder, format, width, height)
        }

    override fun surfaceDestroyed(holder: SurfaceHolder) = dispatchAndWaitFor {
        delegate.surfaceDestroyed(holder)
    }

    private fun dispatchAndWaitFor(runnable: Runnable) {
        if (handler.looper === Looper.myLooper()) {
            runnable.run()
            return
        }
        var finished = false
        val condition = reentrantLock.newCondition()
        handler.post {
            try {
                runnable.run()
            } finally {
                finished = true
                reentrantLock.withLock { condition.signal() }
            }
        }
        reentrantLock.withLock {
            while (!finished) {
                condition.await()
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(delegate: SurfaceHolder.Callback): SynchronousSurfaceHolderCallback
    }
}
