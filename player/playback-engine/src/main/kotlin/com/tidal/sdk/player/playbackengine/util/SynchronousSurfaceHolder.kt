package com.tidal.sdk.player.playbackengine.util

import android.view.SurfaceHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * @see [Relevant ExoPlayer GitHub issue](https://github.com/google/ExoPlayer/issues/10880)
 */
@Suppress("TooManyFunctions")
internal class SynchronousSurfaceHolder @AssistedInject constructor(
    @Assisted private val delegate: SurfaceHolder,
    private val synchronousSurfaceHolderCallbackFactory: SynchronousSurfaceHolderCallback.Factory,
) : SurfaceHolder by delegate {

    private val callbackMap =
        mutableMapOf<SurfaceHolder.Callback, SynchronousSurfaceHolderCallback>()

    override fun addCallback(callback: SurfaceHolder.Callback?) {
        if (callback == null) {
            return
        }
        synchronized(callbackMap) {
            val synchronousSurfaceHolderCallback =
                synchronousSurfaceHolderCallbackFactory.create(callback)
            callbackMap[callback] = synchronousSurfaceHolderCallback
            delegate.addCallback(synchronousSurfaceHolderCallback)
        }
    }

    override fun removeCallback(callback: SurfaceHolder.Callback?) {
        if (callback == null) {
            return
        }
        synchronized(callbackMap) {
            val synchronousSurfaceHolderCallback = callbackMap[callback] ?: return
            delegate.removeCallback(synchronousSurfaceHolderCallback)
            callbackMap.remove(callback)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(delegate: SurfaceHolder): SynchronousSurfaceHolder
    }
}
