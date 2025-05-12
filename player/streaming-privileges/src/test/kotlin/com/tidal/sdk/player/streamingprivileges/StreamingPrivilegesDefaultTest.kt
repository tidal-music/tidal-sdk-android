package com.tidal.sdk.player.streamingprivileges

import android.os.Handler
import assertk.assertThat
import assertk.assertions.isFalse
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.streamingprivileges.acquire.AcquireRunnable
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class StreamingPrivilegesDefaultTest {

    private val networkInteractionsHandler = mock<Handler>()
    private val setKeepAliveRunnableFactory = mock<SetKeepAliveRunnable.Factory>()
    private val setStreamingPrivilegesListenerRunnableFactory =
        mock<SetStreamingPrivilegesListenerRunnable.Factory>()
    private val releaseRunnable = mock<ReleaseRunnable>()
    private val acquireRunnableFactory = mock<AcquireRunnable.Factory>()
    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val mutableState = mock<MutableState>()
    private val streamingPrivilegesDefault =
        StreamingPrivilegesDefault(
            networkInteractionsHandler,
            setKeepAliveRunnableFactory,
            setStreamingPrivilegesListenerRunnableFactory,
            releaseRunnable,
            acquireRunnableFactory,
            trueTimeWrapper,
            mutableState,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            networkInteractionsHandler,
            setKeepAliveRunnableFactory,
            setStreamingPrivilegesListenerRunnableFactory,
            releaseRunnable,
            acquireRunnableFactory,
            trueTimeWrapper,
            mutableState,
        )

    @Test
    fun setKeepAlivePostsRunnable() {
        val setKeepAliveRunnable = mock<SetKeepAliveRunnable>()
        val newValue = true
        whenever(setKeepAliveRunnableFactory.create(true)) doReturn setKeepAliveRunnable

        streamingPrivilegesDefault.setKeepAlive(newValue)

        verify(setKeepAliveRunnableFactory).create(newValue)
        verify(networkInteractionsHandler).post(setKeepAliveRunnable)
        verifyNoInteractions(setKeepAliveRunnable)
    }

    @Test
    fun setListenerSetsMutableStateListener() {
        val streamingPrivilegesListener = mock<StreamingPrivilegesListener>()
        val setStreamingPrivilegesListenerRunnable = mock<SetStreamingPrivilegesListenerRunnable>()
        whenever(setStreamingPrivilegesListenerRunnableFactory.create(streamingPrivilegesListener))
            .thenReturn(setStreamingPrivilegesListenerRunnable)

        streamingPrivilegesDefault.setStreamingPrivilegesListener(streamingPrivilegesListener)

        verify(setStreamingPrivilegesListenerRunnableFactory).create(streamingPrivilegesListener)
        verify(networkInteractionsHandler).post(setStreamingPrivilegesListenerRunnable)
        verifyNoInteractions(streamingPrivilegesListener, setStreamingPrivilegesListenerRunnable)
    }

    @Test
    fun release() {
        streamingPrivilegesDefault.release()

        verify(networkInteractionsHandler).post(releaseRunnable)
    }

    @Test
    fun acquirePostsRunnableHappyPath() {
        val startedAtMillis = 7L
        whenever(trueTimeWrapper.currentTimeMillis) doReturn startedAtMillis
        val connectionMutableState = mock<ConnectionMutableState>()
        whenever(mutableState.connectionMutableState) doReturn connectionMutableState
        val acquireRunnable = mock<AcquireRunnable>()
        whenever(acquireRunnableFactory.create(startedAtMillis, connectionMutableState))
            .thenReturn(acquireRunnable)

        streamingPrivilegesDefault.acquireStreamingPrivileges()

        verify(trueTimeWrapper).currentTimeMillis
        verify(mutableState).connectionMutableState
        verify(acquireRunnableFactory).create(startedAtMillis, connectionMutableState)
        verify(networkInteractionsHandler).post(acquireRunnable)
        verifyNoInteractions(connectionMutableState, acquireRunnable)
    }

    @Test
    fun acquirePostsRunnableNullConnectionMutableState() {
        val actual = streamingPrivilegesDefault.acquireStreamingPrivileges()

        verify(mutableState).connectionMutableState
        assertThat(actual).isFalse()
    }
}
