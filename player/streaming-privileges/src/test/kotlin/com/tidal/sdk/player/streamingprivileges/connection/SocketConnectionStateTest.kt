package com.tidal.sdk.player.streamingprivileges.connection

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import okhttp3.WebSocket
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class SocketConnectionStateTest {

    @Test
    fun failedAttemptsOrZeroOnAwaitingBackOffExpiryReturnsActualFailedAttempts() {
        val expected = -83
        failedAttemptsOrZeroOnConnectingReturnsActualFailedAttempts(
            spy(SocketConnectionState.Connecting.AwaitingBackOffExpiry(0, expected)),
            expected,
        )
    }

    @Test
    fun failedAttemptsOrZeroOnForRealReturnsActualFailedAttempts() {
        val expected = 5
        failedAttemptsOrZeroOnConnectingReturnsActualFailedAttempts(
            spy(SocketConnectionState.Connecting.ForReal(expected)),
            expected,
        )
    }

    @Test
    fun failedAttemptsOrZeroOnNotConnectedReturnsHardcodedZero() =
        failedAttemptsOrZeroOnNonConnectingReturnsHardcodedZero(
            spy(SocketConnectionState.NotConnected)
        )

    @Test
    fun failedAttemptsOrZeroOnConnectedReturnsHardcodedZero() {
        val webSocket = mock<WebSocket>()

        failedAttemptsOrZeroOnNonConnectingReturnsHardcodedZero(
            spy(SocketConnectionState.Connected(webSocket))
        )

        verifyNoInteractions(webSocket)
    }

    private fun failedAttemptsOrZeroOnConnectingReturnsActualFailedAttempts(
        connecting: SocketConnectionState.Connecting,
        expected: Int,
    ) {
        val actual = connecting.failedAttemptsOrZero
        verify(connecting).failedAttemptsOrZero

        verify(connecting).failedAttempts
        assertThat(actual).isEqualTo(expected)
        verifyNoMoreInteractions(connecting)
    }

    private fun failedAttemptsOrZeroOnNonConnectingReturnsHardcodedZero(
        nonConnecting: SocketConnectionState
    ) {
        whenever(nonConnecting.failedAttemptsOrZero).thenCallRealMethod()
        val actual = nonConnecting.failedAttemptsOrZero
        verify(nonConnecting).failedAttemptsOrZero

        assertThat(actual).isZero()
        verifyNoMoreInteractions(nonConnecting)
    }
}
