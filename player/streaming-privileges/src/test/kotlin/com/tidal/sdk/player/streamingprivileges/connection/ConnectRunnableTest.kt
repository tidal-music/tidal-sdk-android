package com.tidal.sdk.player.streamingprivileges.connection

import android.os.Handler
import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.commonandroid.SystemClockWrapper
import com.tidal.sdk.player.streamingprivileges.MutableState
import com.tidal.sdk.player.streamingprivileges.RegisterDefaultNetworkCallbackRunnable
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesEventDispatcher
import com.tidal.sdk.player.streamingprivileges.connection.websocketevents.DumpCallbacksToHandlerWebSocketListener
import java.net.HttpURLConnection
import kotlin.math.pow
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.Call
import retrofit2.Response

@SuppressWarnings("LongMethod")
internal class ConnectRunnableTest {

    private val networkInteractionsHandler = mock<Handler>()
    private val mutableState = mock<MutableState>()
    private val streamingPrivilegesEventDispatcher = mock<StreamingPrivilegesEventDispatcher>()
    private val systemClockWrapper = mock<SystemClockWrapper>()
    private val okHttpClient = mock<OkHttpClient>()
    private val streamingPrivilegesService = mock<StreamingPrivilegesService>()
    private val webSocketConnectionRequestFactory = mock<WebSocketConnectionRequestFactory>()
    private val dumpCallbacksToHandlerWebSocketListenerFactory =
        mock<DumpCallbacksToHandlerWebSocketListener.Factory>()
    private val awaitingBackOffExpiryFactory =
        mock<SocketConnectionState.Connecting.AwaitingBackOffExpiry.Factory>()
    private val forRealFactory = mock<SocketConnectionState.Connecting.ForReal.Factory>()
    private val registerDefaultNetworkCallbackRunnableFactory =
        mock<RegisterDefaultNetworkCallbackRunnable.Factory>()
    private val connectRunnable = ConnectRunnable(
        networkInteractionsHandler,
        mutableState,
        streamingPrivilegesEventDispatcher,
        systemClockWrapper,
        okHttpClient,
        streamingPrivilegesService,
        webSocketConnectionRequestFactory,
        dumpCallbacksToHandlerWebSocketListenerFactory,
        awaitingBackOffExpiryFactory,
        forRealFactory,
        registerDefaultNetworkCallbackRunnableFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        networkInteractionsHandler,
        mutableState,
        streamingPrivilegesEventDispatcher,
        systemClockWrapper,
        okHttpClient,
        streamingPrivilegesService,
        webSocketConnectionRequestFactory,
        dumpCallbacksToHandlerWebSocketListenerFactory,
        awaitingBackOffExpiryFactory,
        forRealFactory,
        registerDefaultNetworkCallbackRunnableFactory,
    )

    @Test
    fun runSuccessfullyWhenKeepAliveIsFalse() {
        connectRunnable.run()

        verify(mutableState).keepAlive
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun runSuccessfullyWhenNotConnected(isNetworkConnectivityCallbackCurrentlyRegistered: Boolean) {
        val notConnected = mock<SocketConnectionState.NotConnected>()

        testSuccessfully(isNetworkConnectivityCallbackCurrentlyRegistered, notConnected, 0)

        verifyNoMoreInteractions(notConnected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun runWithErrorWhenNotConnected(isNetworkConnectivityCallbackCurrentlyRegistered: Boolean) {
        val notConnected = mock<SocketConnectionState.NotConnected>()
        val upTimeMillis = -7L
        whenever(systemClockWrapper.uptimeMillis) doReturn upTimeMillis

        testWithError(
            isNetworkConnectivityCallbackCurrentlyRegistered,
            upTimeMillis,
            1,
            notConnected,
            0,
        )

        verifyNoMoreInteractions(notConnected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun runWhenConnectingForReal(isNetworkConnectivityCallbackCurrentlyRegistered: Boolean) {
        val socketConnectionState = mock<SocketConnectionState.Connecting.ForReal>()
        val connectionMutableState = mock<ConnectionMutableState> {
            on { it.socketConnectionState } doReturn socketConnectionState
        }
        whenever(mutableState.keepAlive) doReturn true
        whenever(mutableState.isNetworkConnectivityCallbackCurrentlyRegistered)
            .thenReturn(isNetworkConnectivityCallbackCurrentlyRegistered)
        val registerDefaultNetworkCallbackRunnable = mock<RegisterDefaultNetworkCallbackRunnable>()
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            whenever(registerDefaultNetworkCallbackRunnableFactory.create())
                .thenReturn(registerDefaultNetworkCallbackRunnable)
        }
        whenever(mutableState.connectionMutableState) doReturn connectionMutableState

        connectRunnable.run()

        verify(mutableState).keepAlive
        verify(mutableState).isNetworkConnectivityCallbackCurrentlyRegistered
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            verify(registerDefaultNetworkCallbackRunnableFactory).create()
            verify(registerDefaultNetworkCallbackRunnable).run()
        }
        verify(mutableState).connectionMutableState
        verify(mutableState).connectionMutableState = connectionMutableState
        verify(connectionMutableState).socketConnectionState
        verifyNoMoreInteractions(socketConnectionState, connectionMutableState)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun runWhenConnected(isNetworkConnectivityCallbackCurrentlyRegistered: Boolean) {
        val socketConnectionState = mock<SocketConnectionState.Connected>()
        val connectionMutableState = mock<ConnectionMutableState> {
            on { it.socketConnectionState } doReturn socketConnectionState
        }
        whenever(mutableState.keepAlive) doReturn true
        whenever(mutableState.isNetworkConnectivityCallbackCurrentlyRegistered)
            .thenReturn(isNetworkConnectivityCallbackCurrentlyRegistered)
        val registerDefaultNetworkCallbackRunnable = mock<RegisterDefaultNetworkCallbackRunnable>()
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            whenever(registerDefaultNetworkCallbackRunnableFactory.create())
                .thenReturn(registerDefaultNetworkCallbackRunnable)
        }
        whenever(mutableState.connectionMutableState) doReturn connectionMutableState

        connectRunnable.run()

        verify(mutableState).keepAlive
        verify(mutableState).isNetworkConnectivityCallbackCurrentlyRegistered
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            verify(registerDefaultNetworkCallbackRunnableFactory).create()
            verify(registerDefaultNetworkCallbackRunnable).run()
        }
        verify(mutableState).connectionMutableState
        verify(mutableState).connectionMutableState = connectionMutableState
        verify(connectionMutableState).socketConnectionState
        verify(streamingPrivilegesEventDispatcher)
            .dispatchConnectionEstablished(connectionMutableState)
        verifyNoMoreInteractions(socketConnectionState, connectionMutableState)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun runWhenAwaitingNotYetExpiredBackOff(
        isNetworkConnectivityCallbackCurrentlyRegistered: Boolean,
    ) {
        val retryAtMillis = 1L
        val socketConnectionState = mock<SocketConnectionState.Connecting.AwaitingBackOffExpiry> {
            on { it.failedAttempts } doReturn 1
            on { it.retryAtMillis } doReturn retryAtMillis
        }
        val connectionMutableState = mock<ConnectionMutableState> {
            on { it.socketConnectionState } doReturn socketConnectionState
        }
        whenever(mutableState.keepAlive) doReturn true
        whenever(mutableState.isNetworkConnectivityCallbackCurrentlyRegistered)
            .thenReturn(isNetworkConnectivityCallbackCurrentlyRegistered)
        val registerDefaultNetworkCallbackRunnable = mock<RegisterDefaultNetworkCallbackRunnable>()
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            whenever(registerDefaultNetworkCallbackRunnableFactory.create())
                .thenReturn(registerDefaultNetworkCallbackRunnable)
        }
        whenever(systemClockWrapper.uptimeMillis) doReturn retryAtMillis - 1
        whenever(mutableState.connectionMutableState) doReturn connectionMutableState

        connectRunnable.run()

        verify(mutableState).keepAlive
        verify(mutableState).isNetworkConnectivityCallbackCurrentlyRegistered
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            verify(registerDefaultNetworkCallbackRunnableFactory).create()
            verify(registerDefaultNetworkCallbackRunnable).run()
        }
        verify(mutableState).connectionMutableState
        verify(mutableState).connectionMutableState = connectionMutableState
        verify(connectionMutableState, atLeastOnce()).socketConnectionState
        verify(socketConnectionState).failedAttempts
        verify(socketConnectionState).retryAtMillis
        verify(systemClockWrapper).uptimeMillis
        verifyNoMoreInteractions(socketConnectionState, connectionMutableState)
    }

    @ParameterizedTest
    @MethodSource("combinationsForRunWithExpiredBackoff")
    fun runSuccessfullyWhenAwaitingExpiredBackOff(
        isNetworkConnectivityCallbackCurrentlyRegistered: Boolean,
        failedAttempts: Int,
    ) {
        val retryAtMillis = 0L
        val awaitingBackOffExpiry = mock<SocketConnectionState.Connecting.AwaitingBackOffExpiry> {
            on { it.retryAtMillis } doReturn retryAtMillis
            on { it.failedAttempts } doReturn failedAttempts
            on { it.failedAttemptsOrZero } doReturn failedAttempts
        }
        whenever(systemClockWrapper.uptimeMillis) doReturn retryAtMillis + 1

        testSuccessfully(
            isNetworkConnectivityCallbackCurrentlyRegistered,
            awaitingBackOffExpiry,
            failedAttempts,
        )

        verify(mutableState).connectionMutableState
        verify(awaitingBackOffExpiry).failedAttempts
        verify(awaitingBackOffExpiry).retryAtMillis
        verify(systemClockWrapper).uptimeMillis
        verifyNoMoreInteractions(awaitingBackOffExpiry)
    }

    @ParameterizedTest
    @MethodSource("combinationsForRunWithExpiredBackoff")
    fun runWithErrorWhenAwaitingExpiredBackOff(
        isNetworkConnectivityCallbackCurrentlyRegistered: Boolean,
        failedAttempts: Int,
    ) {
        val retryAtMillis = -1L
        val upTimeMillis = -9L
        val awaitingBackOffExpiry = mock<SocketConnectionState.Connecting.AwaitingBackOffExpiry> {
            on { it.retryAtMillis } doReturn retryAtMillis
            on { it.failedAttempts } doReturn failedAttempts
            on { it.failedAttemptsOrZero } doReturn failedAttempts
        }
        whenever(systemClockWrapper.uptimeMillis).thenReturn(retryAtMillis + 1, upTimeMillis)

        testWithError(
            isNetworkConnectivityCallbackCurrentlyRegistered,
            upTimeMillis,
            2,
            awaitingBackOffExpiry,
            failedAttempts,
        )

        verify(mutableState).connectionMutableState
        verify(awaitingBackOffExpiry).failedAttempts
        verify(awaitingBackOffExpiry).retryAtMillis
        verifyNoMoreInteractions(awaitingBackOffExpiry)
    }

    private fun testSuccessfully(
        isNetworkConnectivityCallbackCurrentlyRegistered: Boolean,
        prevState: SocketConnectionState,
        failedAttempts: Int,
    ) {
        whenever(mutableState.keepAlive) doReturn true
        whenever(mutableState.isNetworkConnectivityCallbackCurrentlyRegistered)
            .thenReturn(isNetworkConnectivityCallbackCurrentlyRegistered)
        val registerDefaultNetworkCallbackRunnable = mock<RegisterDefaultNetworkCallbackRunnable>()
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            whenever(registerDefaultNetworkCallbackRunnableFactory.create())
                .thenReturn(registerDefaultNetworkCallbackRunnable)
        }
        val forReal = mock<SocketConnectionState.Connecting.ForReal>()
        whenever(forRealFactory.create(failedAttempts)) doReturn forReal
        val url = "socket url"
        val streamingPrivilegesWebSocketInfo = mock<StreamingPrivilegesWebSocketInfo> {
            on { it.url } doReturn url
        }
        val response = mock<Response<StreamingPrivilegesWebSocketInfo>> {
            on { it.code() } doReturn HttpURLConnection.HTTP_OK
            on { it.body() } doReturn streamingPrivilegesWebSocketInfo
        }
        val call = mock<Call<StreamingPrivilegesWebSocketInfo>> {
            on { it.execute() } doReturn response
        }
        whenever(streamingPrivilegesService.getStreamingPrivilegesWebSocketInfo()) doReturn call
        val webSocketConnectionRequest = mock<Request>()
        whenever(webSocketConnectionRequestFactory.create(url)) doReturn webSocketConnectionRequest
        val dumpCallbacksToHandlerWebSocketListener =
            mock<DumpCallbacksToHandlerWebSocketListener>()
        val connectionMutableState = mock<ConnectionMutableState> {
            on { it.socketConnectionState } doReturn prevState doReturn forReal
        }
        whenever(mutableState.connectionMutableState) doReturn connectionMutableState
        whenever(dumpCallbacksToHandlerWebSocketListenerFactory.create(connectionMutableState))
            .thenReturn(dumpCallbacksToHandlerWebSocketListener)

        connectRunnable.run()

        verify(mutableState).keepAlive
        verify(mutableState).isNetworkConnectivityCallbackCurrentlyRegistered
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            verify(registerDefaultNetworkCallbackRunnableFactory).create()
            verify(registerDefaultNetworkCallbackRunnable).run()
        }
        verify(mutableState).connectionMutableState
        verify(mutableState).connectionMutableState = connectionMutableState
        verify(connectionMutableState).socketConnectionState
        verify(prevState).failedAttemptsOrZero
        verify(forRealFactory).create(failedAttempts)
        verify(connectionMutableState).socketConnectionState = forReal
        verify(streamingPrivilegesService).getStreamingPrivilegesWebSocketInfo()
        verify(call).execute()
        verify(response).code()
        verify(response).body()
        verify(streamingPrivilegesWebSocketInfo).url
        verify(webSocketConnectionRequestFactory).create(url)
        verify(dumpCallbacksToHandlerWebSocketListenerFactory).create(connectionMutableState)
        verify(okHttpClient).newWebSocket(
            webSocketConnectionRequest,
            dumpCallbacksToHandlerWebSocketListener,
        )
        verifyNoMoreInteractions(
            connectionMutableState,
            registerDefaultNetworkCallbackRunnable,
            forReal,
            streamingPrivilegesWebSocketInfo,
            response,
            call,
            webSocketConnectionRequest,
            dumpCallbacksToHandlerWebSocketListener,
        )
    }

    private fun testWithError(
        isNetworkConnectivityCallbackCurrentlyRegistered: Boolean,
        upTimeMillis: Long,
        upTimeMillisTimes: Int,
        prevState: SocketConnectionState,
        failedAttempts: Int,
    ) {
        whenever(mutableState.keepAlive) doReturn true
        whenever(mutableState.isNetworkConnectivityCallbackCurrentlyRegistered)
            .thenReturn(isNetworkConnectivityCallbackCurrentlyRegistered)
        val registerDefaultNetworkCallbackRunnable = mock<RegisterDefaultNetworkCallbackRunnable>()
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            whenever(registerDefaultNetworkCallbackRunnableFactory.create())
                .thenReturn(registerDefaultNetworkCallbackRunnable)
        }
        val nextFailedAttempts =
            (failedAttempts + 1).coerceAtMost(ConnectRunnable.ACCOUNTABLE_ATTEMPTS_MAX)
        val forReal = mock<SocketConnectionState.Connecting.ForReal> {
            on { failedAttemptsOrZero } doReturn failedAttempts
        }
        whenever(forRealFactory.create(failedAttempts)) doReturn forReal
        val uncheckedException = mock<RuntimeException>()
        whenever(streamingPrivilegesService.getStreamingPrivilegesWebSocketInfo())
            .thenThrow(uncheckedException)
        val awaitingBackOffExpiry = mock<SocketConnectionState.Connecting.AwaitingBackOffExpiry>()
        whenever(awaitingBackOffExpiryFactory.create(any(), eq(nextFailedAttempts)))
            .thenReturn(awaitingBackOffExpiry)
        val retryAtMillisCaptor = argumentCaptor<Long>()
        val maxDelayMs = ConnectRunnable.reflectionDELAY_BASE_MS *
            2.0.pow(nextFailedAttempts).toLong()
        val adjustedDelayMsCaptor = argumentCaptor<Long>()
        val connectionMutableState = mock<ConnectionMutableState> {
            on { it.socketConnectionState } doReturn prevState doReturn forReal
        }
        whenever(mutableState.connectionMutableState) doReturn connectionMutableState

        connectRunnable.run()

        verify(mutableState).keepAlive
        verify(mutableState).isNetworkConnectivityCallbackCurrentlyRegistered
        if (!isNetworkConnectivityCallbackCurrentlyRegistered) {
            verify(registerDefaultNetworkCallbackRunnableFactory).create()
            verify(registerDefaultNetworkCallbackRunnable).run()
        }
        verify(mutableState).connectionMutableState
        verify(mutableState).connectionMutableState = connectionMutableState
        verify(connectionMutableState, atLeastOnce()).socketConnectionState
        verify(prevState).failedAttemptsOrZero
        verify(forRealFactory).create(failedAttempts)
        inOrder(connectionMutableState).apply {
            verify(connectionMutableState).socketConnectionState = forReal
            verify(connectionMutableState).socketConnectionState = awaitingBackOffExpiry
        }
        verify(streamingPrivilegesService).getStreamingPrivilegesWebSocketInfo()
        verify(systemClockWrapper, times(upTimeMillisTimes)).uptimeMillis
        verify(forReal).failedAttemptsOrZero
        verify(awaitingBackOffExpiryFactory)
            .create(retryAtMillisCaptor.capture(), eq(nextFailedAttempts))
        verify(networkInteractionsHandler).postDelayed(
            eq(connectRunnable),
            adjustedDelayMsCaptor.capture(),
        )
        assertThat(retryAtMillisCaptor.firstValue - upTimeMillis)
            .isEqualTo(adjustedDelayMsCaptor.firstValue)
        assertThat(adjustedDelayMsCaptor.firstValue)
            .isBetween(
                (maxDelayMs * (1 - ConnectRunnable.reflectionJITTER_FACTOR_MAX)).toLong(),
                maxDelayMs,
            )
        verifyNoMoreInteractions(
            forReal,
            uncheckedException,
            awaitingBackOffExpiry,
        )
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember", "NestedBlockDepth")
        private fun combinationsForRunWithExpiredBackoff() = mutableSetOf(
            Arguments.of(true, ConnectRunnable.ACCOUNTABLE_ATTEMPTS_MAX - 1),
            Arguments.of(true, ConnectRunnable.ACCOUNTABLE_ATTEMPTS_MAX),
            Arguments.of(false, ConnectRunnable.ACCOUNTABLE_ATTEMPTS_MAX - 1),
            Arguments.of(false, ConnectRunnable.ACCOUNTABLE_ATTEMPTS_MAX),
        )
    }
}
