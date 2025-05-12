package com.tidal.sdk.player.streamingprivileges

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions

internal class StreamingPrivilegesEventDispatcherTest {

    private val networkInteractionsHandler = mock<Handler>()
    private val streamingPrivilegesEventDispatcher =
        StreamingPrivilegesEventDispatcher(networkInteractionsHandler)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(networkInteractionsHandler)

    @Test
    fun dispatchConnectionEstablished() {
        val runnableCaptor = argumentCaptor<Runnable>()
        val streamingPrivilegesListener = mock<StreamingPrivilegesListener>()
        val connectionMutableState =
            mock<ConnectionMutableState> {
                on { it.streamingPrivilegesListener } doReturn streamingPrivilegesListener
            }

        streamingPrivilegesEventDispatcher.dispatchConnectionEstablished(connectionMutableState)

        verify(networkInteractionsHandler).post(runnableCaptor.capture())
        verifyNoInteractions(connectionMutableState, streamingPrivilegesListener)

        runnableCaptor.firstValue.run()

        verify(connectionMutableState).streamingPrivilegesListener
        verify(streamingPrivilegesListener).onConnectionEstablished()
        verifyNoMoreInteractions(streamingPrivilegesListener)
    }

    @Test
    fun dispatchStreamingPrivilegeRevoked() {
        val privilegedClientDisplayName = "privilegedClientDisplayName"
        val runnableCaptor = argumentCaptor<Runnable>()
        val streamingPrivilegesListener = mock<StreamingPrivilegesListener>()
        val connectionMutableState =
            mock<ConnectionMutableState> {
                on { it.streamingPrivilegesListener } doReturn streamingPrivilegesListener
            }

        streamingPrivilegesEventDispatcher.dispatchStreamingPrivilegeRevoked(
            connectionMutableState,
            privilegedClientDisplayName,
        )

        verify(networkInteractionsHandler).post(runnableCaptor.capture())
        verifyNoInteractions(streamingPrivilegesListener, connectionMutableState)

        runnableCaptor.firstValue.run()

        verify(connectionMutableState).streamingPrivilegesListener
        verify(streamingPrivilegesListener)
            .onStreamingPrivilegesRevoked(privilegedClientDisplayName)
        verifyNoMoreInteractions(streamingPrivilegesListener)
    }
}
