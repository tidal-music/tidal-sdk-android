package com.tidal.sdk.flo.core.internal

import android.os.Handler

internal class PostRunnableToHandlerFunction<T>(
    private val targetHandler: Handler,
    private val runnableFactory: (T) -> Runnable,
) : (T) -> Unit {

    override fun invoke(data: T) {
        targetHandler.post(runnableFactory(data))
    }
}
