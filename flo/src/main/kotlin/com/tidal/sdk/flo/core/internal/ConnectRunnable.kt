package com.tidal.sdk.flo.core.internal

import android.os.Handler
import android.os.SystemClock
import com.squareup.moshi.Moshi
import com.tidal.sdk.flo.core.FloException
import java.util.Locale
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

internal class ConnectRunnable
// No DI framework to avoid classpath bloat so need to pipe dependencies explicitly
@Suppress("LongParameterList")
constructor(
    private val url: String,
    private val connectionMutableState: SubscriptionManager.ConnectionMutableState,
    private val operationHandler: Handler,
    private val okHttpClient: OkHttpClient,
    private val tokenProvider: () -> String?,
    private val retryUponAuthorizationError: (Response) -> Boolean,
    private val moshi: Moshi,
    private val terminalErrorManager: TerminalErrorManager,
    private val backoffPolicy: BackoffPolicy,
    private val failedAttempts: Int = 0,
) : Runnable {

    // ReturnCount -> Preferred over indenting
    // TooGenericExceptionCaught -> We don't want singled-out behavior
    @Suppress("ReturnCount", "TooGenericExceptionCaught")
    override fun run() {
        if (!connectionMutableState.isConnectionRequired) {
            return
        }
        when (val connectionState = connectionMutableState.socketConnectionState) {
            is SocketConnectionState.NotConnected -> Unit
            is SocketConnectionState.Connecting.AwaitingBackoffExpiry ->
                if (
                    failedAttempts != 0 &&
                    connectionState.retryAtMillis > SystemClock.uptimeMillis()
                ) {
                    return
                }

            else -> return
        }
        connectionMutableState.socketConnectionState = SocketConnectionState.Connecting.ForReal
        try {
            okHttpClient.newWebSocket(
                Request.Builder()
                    .apply {
                        val token = tokenProvider() ?: return
                        header(
                            HEADER_NAME_AUTHORIZATION,
                            TEMPLATE_HEADER_VALUE_BEARER_AUTHORIZATION.format(
                                token,
                                Locale.ENGLISH
                            ),
                        )
                    }
                    .url(url)
                    .build(),
                ConnectionWebSocketListener(
                    url,
                    connectionMutableState,
                    operationHandler,
                    okHttpClient,
                    tokenProvider,
                    retryUponAuthorizationError,
                    moshi,
                    terminalErrorManager,
                    backoffPolicy,
                ),
            )
        } catch (throwable: Throwable) {
            retryIfBackoffAllows(throwable)
        }
    }

    private fun retryIfBackoffAllows(throwable: Throwable) {
        val failedAttemptsIncludingSelf = failedAttempts + 1
        val nextAttemptDelayMillis = backoffPolicy.onFailedAttempt(failedAttemptsIncludingSelf)
        if (nextAttemptDelayMillis == null) {
            terminalErrorManager.dispatchErrorAndTerminateConnection(
                connectionMutableState,
                FloException.ConnectionLost(throwable),
            )
            return
        }
        val nextAttemptAtMillis = SystemClock.uptimeMillis() + nextAttemptDelayMillis
        connectionMutableState.socketConnectionState =
            SocketConnectionState.Connecting.AwaitingBackoffExpiry(nextAttemptAtMillis)
        operationHandler.postAtTime(
            ConnectRunnable(
                url,
                connectionMutableState,
                operationHandler,
                okHttpClient,
                tokenProvider,
                retryUponAuthorizationError,
                moshi,
                terminalErrorManager,
                backoffPolicy,
                failedAttemptsIncludingSelf,
            ),
            nextAttemptAtMillis,
        )
    }

    companion object {
        private const val HEADER_NAME_AUTHORIZATION = "Authorization"
        private const val TEMPLATE_HEADER_VALUE_BEARER_AUTHORIZATION = "Bearer %s"
    }
}
