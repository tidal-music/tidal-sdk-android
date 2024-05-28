package com.tidal.sdk.player.streamingprivileges

import android.os.Handler
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.streamingprivileges.acquire.AcquireRunnable

@Suppress("LongParameterList", "MaxLineLength")
internal class StreamingPrivilegesDefault(
    private val networkInteractionsHandler: Handler,
    private val setKeepAliveRunnableFactory: SetKeepAliveRunnable.Factory,
    private val setStreamingPrivilegesListenerRunnableFactory: SetStreamingPrivilegesListenerRunnable.Factory, // ktlint-disable max-line-length parameter-wrapping
    private val releaseRunnable: ReleaseRunnable,
    private val acquireRunnableFactory: AcquireRunnable.Factory,
    private val trueTimeWrapper: TrueTimeWrapper,
    private val mutableState: MutableState,
) : StreamingPrivileges {

    override fun setKeepAlive(newValue: Boolean) = networkInteractionsHandler.post(
        setKeepAliveRunnableFactory.create(newValue),
    )

    override fun setStreamingPrivilegesListener(
        streamingPrivilegesListener: StreamingPrivilegesListener?,
    ) = networkInteractionsHandler
        .post(setStreamingPrivilegesListenerRunnableFactory.create(streamingPrivilegesListener))

    override fun acquireStreamingPrivileges() = mutableState.connectionMutableState?.let {
        networkInteractionsHandler.post(
            acquireRunnableFactory.create(trueTimeWrapper.currentTimeMillis, it),
        )
    } ?: false

    override fun release() = networkInteractionsHandler.post(releaseRunnable)
}
