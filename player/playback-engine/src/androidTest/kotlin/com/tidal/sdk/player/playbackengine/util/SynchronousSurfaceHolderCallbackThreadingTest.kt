package com.tidal.sdk.player.playbackengine.util

import android.os.Handler
import android.os.HandlerThread
import android.view.SurfaceHolder
import assertk.assertThat
import assertk.assertions.isSameAs
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoInteractions

internal class SynchronousSurfaceHolderCallbackThreadingTest {

    private val reentrantLock = ReentrantLock()
    private val delegateWaitCondition = reentrantLock.newCondition()
    private lateinit var executionThread: Thread
    private val handlerThread = HandlerThread("TestThread").apply { start() }
    private val handler = Handler(handlerThread.looper)
    private val delegate =
        object : SurfaceHolder.Callback {

            override fun surfaceCreated(holder: SurfaceHolder) = assignThreadAndReleaseLock()

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int,
            ) = assignThreadAndReleaseLock()

            override fun surfaceDestroyed(holder: SurfaceHolder) = assignThreadAndReleaseLock()

            private fun assignThreadAndReleaseLock() {
                executionThread = Thread.currentThread()
                reentrantLock.withLock { delegateWaitCondition.signal() }
            }
        }
    private val synchronousSurfaceHolderCallback =
        SynchronousSurfaceHolderCallback(handler, delegate)

    @Test
    fun surfaceCreatedFromDifferentThread() = testCallFromDifferentThread {
        synchronousSurfaceHolderCallback.surfaceCreated(it)
    }

    @Test
    fun surfaceCreatedFromSameThread() = testCallFromSameThread {
        synchronousSurfaceHolderCallback.surfaceCreated(it)
    }

    @Test
    fun surfaceChangedFromDifferentThread() = testCallFromDifferentThread {
        synchronousSurfaceHolderCallback.surfaceChanged(it, -1, -2, -3)
    }

    @Test
    fun surfaceChangedFromSameThread() = testCallFromSameThread {
        synchronousSurfaceHolderCallback.surfaceChanged(it, -1, -2, -3)
    }

    @Test
    fun surfaceDestroyedFromDifferentThread() = testCallFromDifferentThread {
        synchronousSurfaceHolderCallback.surfaceDestroyed(it)
    }

    @Test
    fun surfaceDestroyedFromSameThread() = testCallFromSameThread {
        synchronousSurfaceHolderCallback.surfaceDestroyed(it)
    }

    private fun testCallFromDifferentThread(block: (SurfaceHolder) -> Unit) {
        val surfaceHolder = mock<SurfaceHolder>()

        block(surfaceHolder)
        reentrantLock.withLock {
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }

        assertThat(executionThread).isSameAs(handlerThread)
        verifyNoInteractions(surfaceHolder)
    }

    private fun testCallFromSameThread(block: (SurfaceHolder) -> Unit) {
        val surfaceHolder = mock<SurfaceHolder>()

        handler.post { block(surfaceHolder) }
        reentrantLock.withLock {
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }

        assertThat(executionThread).isSameAs(handlerThread)
        verifyNoInteractions(surfaceHolder)
    }
}
