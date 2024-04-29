package com.tidal.sdk.player.streamingprivileges.connection

import android.os.Handler
import androidx.annotation.VisibleForTesting
import com.tidal.sdk.player.commonandroid.SystemClockWrapper
import com.tidal.sdk.player.streamingprivileges.MutableState
import com.tidal.sdk.player.streamingprivileges.RegisterDefaultNetworkCallbackRunnable
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesEventDispatcher
import com.tidal.sdk.player.streamingprivileges.connection.websocketevents.DumpCallbacksToHandlerWebSocketListener
import java.net.HttpURLConnection
import kotlin.math.pow
import okhttp3.OkHttpClient

@Suppress("LongParameterList", "MaxLineLength")
internal class ConnectRunnable(
    private val networkInteractionsHandler: Handler,
    private val mutableState: MutableState,
    private val streamingPrivilegesEventDispatcher: StreamingPrivilegesEventDispatcher, // ktlint-disable max-line-length parameter-wrapping
    private val systemClockWrapper: SystemClockWrapper,
    private val okHttpClient: OkHttpClient,
    private val streamingPrivilegesService: StreamingPrivilegesService,
    private val webSocketConnectionRequestFactory: WebSocketConnectionRequestFactory,
    private val dumpCallbacksToHandlerWebSocketListenerFactory: DumpCallbacksToHandlerWebSocketListener.Factory, // ktlint-disable max-line-length parameter-wrapping
    @Suppress("MaxLineLength") private val awaitingBackOffExpiryFactory: SocketConnectionState.Connecting.AwaitingBackOffExpiry.Factory, // ktlint-disable max-line-length parameter-wrapping
    private val forRealFactory: SocketConnectionState.Connecting.ForReal.Factory,
    private val registerDefaultNetworkCallbackRunnableFactory: RegisterDefaultNetworkCallbackRunnable.Factory, // ktlint-disable max-line-length parameter-wrapping
) : Runnable {

    @Suppress("ReturnCount")
    override fun run() {
        if (!mutableState.keepAlive) {
            return
        }
        if (!mutableState.isNetworkConnectivityCallbackCurrentlyRegistered) {
            registerDefaultNetworkCallbackRunnableFactory.create().run()
        }
        val connectionMutableState = mutableState.connectionMutableState
            ?: ConnectionMutableState(mutableState.streamingPrivilegesListener)
        mutableState.connectionMutableState = connectionMutableState
        val socketConnectionState = connectionMutableState.socketConnectionState
        when (socketConnectionState) {
            is SocketConnectionState.Connected -> {
                streamingPrivilegesEventDispatcher
                    .dispatchConnectionEstablished(connectionMutableState)
                return
            }

            is SocketConnectionState.Connecting.AwaitingBackOffExpiry -> {
                if (socketConnectionState.failedAttempts != 0 &&
                    socketConnectionState.retryAtMillis > systemClockWrapper.uptimeMillis
                ) {
                    return
                }
            }

            is SocketConnectionState.Connecting.ForReal -> return
            else -> Unit
        }
        connectionMutableState.socketConnectionState =
            forRealFactory.create(socketConnectionState.failedAttemptsOrZero)
        try {
            val response = streamingPrivilegesService.getStreamingPrivilegesWebSocketInfo()
                .execute()
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // Cannot be retried
                return
            }
            val url = response.body()!!.url
            okHttpClient.newWebSocket(
                webSocketConnectionRequestFactory.create(url),
                dumpCallbacksToHandlerWebSocketListenerFactory.create(connectionMutableState),
            )
        } catch (ignored: Throwable) {
            backOffAndRetry(connectionMutableState)
        }
    }

    private fun backOffAndRetry(connectionMutableState: ConnectionMutableState) {
        val failedAttempts = (connectionMutableState.socketConnectionState.failedAttemptsOrZero + 1)
            .coerceAtMost(ACCOUNTABLE_ATTEMPTS_MAX)
        val maxDelayMs = DELAY_BASE_MS * 2.0.pow(failedAttempts)
        val jitterFactor = (0..ONE_HUNDRED).random() / ONE_HUNDRED.toDouble() * JITTER_FACTOR_MAX
        val adjustedDelayMs = (maxDelayMs * (1 - JITTER_FACTOR_MAX + jitterFactor)).toLong()
        connectionMutableState.socketConnectionState = awaitingBackOffExpiryFactory.create(
            systemClockWrapper.uptimeMillis + adjustedDelayMs,
            failedAttempts,
        )
        networkInteractionsHandler.postDelayed(this, adjustedDelayMs)
    }

    companion object {
        private const val DELAY_BASE_MS = 1_000
        private const val JITTER_FACTOR_MAX = .2F
        private const val ONE_HUNDRED = 100

        @VisibleForTesting // Can't use a reflection accessor due to being used as test param
        internal const val ACCOUNTABLE_ATTEMPTS_MAX = 6
    }
}
