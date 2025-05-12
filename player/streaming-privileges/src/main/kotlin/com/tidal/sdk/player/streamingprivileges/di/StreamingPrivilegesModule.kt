package com.tidal.sdk.player.streamingprivileges.di

import android.net.ConnectivityManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.google.gson.Gson
import com.tidal.sdk.player.common.Common
import com.tidal.sdk.player.common.RequestBuilderFactory
import com.tidal.sdk.player.commonandroid.SystemClockWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.streamingprivileges.MutableState
import com.tidal.sdk.player.streamingprivileges.RegisterDefaultNetworkCallbackRunnable
import com.tidal.sdk.player.streamingprivileges.ReleaseRunnable
import com.tidal.sdk.player.streamingprivileges.SetKeepAliveRunnable
import com.tidal.sdk.player.streamingprivileges.SetStreamingPrivilegesListenerRunnable
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesDefault
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesEventDispatcher
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesNetworkCallback
import com.tidal.sdk.player.streamingprivileges.acquire.AcquireRunnable
import com.tidal.sdk.player.streamingprivileges.connection.ConnectRunnable
import com.tidal.sdk.player.streamingprivileges.connection.DisconnectRunnable
import com.tidal.sdk.player.streamingprivileges.connection.SocketConnectionState
import com.tidal.sdk.player.streamingprivileges.connection.StreamingPrivilegesService
import com.tidal.sdk.player.streamingprivileges.connection.WebSocketConnectionRequestFactory
import com.tidal.sdk.player.streamingprivileges.connection.websocketevents.DumpCallbacksToHandlerWebSocketListener
import com.tidal.sdk.player.streamingprivileges.connection.websocketevents.OnWebSocketFailure
import com.tidal.sdk.player.streamingprivileges.connection.websocketevents.OnWebSocketMessage
import com.tidal.sdk.player.streamingprivileges.connection.websocketevents.OnWebSocketOpen
import com.tidal.sdk.player.streamingprivileges.messages.incoming.IncomingWebSocketMessageParser
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
internal object StreamingPrivilegesModule {

    @Provides @Reusable fun requestBuilderFactory() = RequestBuilderFactory()

    @Provides
    @Reusable
    fun incomingWebSocketMessageParser(gson: Gson) = IncomingWebSocketMessageParser(gson)

    @Provides
    @Reusable
    fun webSocketConnectionRequestFactory(requestBuilderFactory: RequestBuilderFactory) =
        WebSocketConnectionRequestFactory(requestBuilderFactory)

    @Provides
    @Reusable
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Reusable
    fun retrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory) =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Common.TIDAL_API_ENDPOINT_V1)
            .addConverterFactory(gsonConverterFactory)
            .build()!!

    @Provides
    @Reusable
    fun streamingPrivilegeService(retrofit: Retrofit) =
        retrofit.create(StreamingPrivilegesService::class.java)!!

    @Provides
    @Singleton
    fun networkInteractionsLooper(): Looper =
        HandlerThread("StreamingPrivilegesNetworkInteractionsThread").let {
            it.start()
            it.looper
        }

    @Provides @Reusable fun networkInteractionsHandler(looper: Looper) = Handler(looper)

    @Provides @Reusable fun systemClockWrapper() = SystemClockWrapper()

    @Provides @Singleton fun mutableState() = MutableState()

    @Provides
    @Reusable
    fun streamingPrivilegesNetworkCallback(
        networkInteractionsHandler: Handler,
        connectRunnable: ConnectRunnable,
    ) = StreamingPrivilegesNetworkCallback(networkInteractionsHandler, connectRunnable)

    @Provides
    @Reusable
    fun streamingPrivilegesEventDispatcher(networkInteractionsHandler: Handler) =
        StreamingPrivilegesEventDispatcher(networkInteractionsHandler)

    @Provides
    @Reusable
    fun connectRunnable(
        networkInteractionsHandler: Handler,
        mutableState: MutableState,
        streamingPrivilegesEventDispatcher: StreamingPrivilegesEventDispatcher,
        systemClockWrapper: SystemClockWrapper,
        okHttpClient: OkHttpClient,
        streamingPrivilegesService: StreamingPrivilegesService,
        webSocketConnectionRequestFactory: WebSocketConnectionRequestFactory,
        dumpCallbacksToHandlerWebSocketListenerFactory:
            DumpCallbacksToHandlerWebSocketListener.Factory, // ktlint-disable max-line-length
        // parameter-wrapping
        awaitingBackOffExpiryFactory:
            SocketConnectionState.Connecting.AwaitingBackOffExpiry.Factory, // ktlint-disable
        // max-line-length
        // parameter-wrapping
        forRealFactory: SocketConnectionState.Connecting.ForReal.Factory,
        registerDefaultNetworkCallbackRunnableFactory:
            RegisterDefaultNetworkCallbackRunnable.Factory, // ktlint-disable max-line-length
        // parameter-wrapping
    ) =
        ConnectRunnable(
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

    @Provides
    @Reusable
    fun disconnectRunnable(
        mutableState: MutableState,
        connectivityManager: ConnectivityManager,
        streamingPrivilegesNetworkCallback: StreamingPrivilegesNetworkCallback,
    ) = DisconnectRunnable(mutableState, connectivityManager, streamingPrivilegesNetworkCallback)

    @Provides
    @Reusable
    fun releaseRunnable(mutableState: MutableState, networkInteractionsHandler: Handler) =
        ReleaseRunnable(mutableState, networkInteractionsHandler)

    @Provides
    @Singleton
    fun streamingPrivileges(
        networkInteractionsHandler: Handler,
        releaseRunnable: ReleaseRunnable,
        setKeepAliveRunnableFactory: SetKeepAliveRunnable.Factory,
        setStreamingPrivilegesListenerRunnableFactory:
            SetStreamingPrivilegesListenerRunnable.Factory, // ktlint-disable max-line-length
        // parameter-wrapping
        acquireRunnableFactory: AcquireRunnable.Factory,
        trueTimeWrapper: TrueTimeWrapper,
        mutableState: MutableState,
    ): StreamingPrivileges =
        StreamingPrivilegesDefault(
            networkInteractionsHandler,
            setKeepAliveRunnableFactory,
            setStreamingPrivilegesListenerRunnableFactory,
            releaseRunnable,
            acquireRunnableFactory,
            trueTimeWrapper,
            mutableState,
        )

    @Provides
    @Reusable
    fun onWebSocketFailure(networkInteractionsHandler: Handler, connectRunnable: ConnectRunnable) =
        OnWebSocketFailure(networkInteractionsHandler, connectRunnable)

    @Provides
    @Reusable
    fun onWebSocketMessage(
        networkInteractionsHandler: Handler,
        connectRunnable: ConnectRunnable,
        incomingWebSocketMessageParser: IncomingWebSocketMessageParser,
        streamingPrivilegesEventDispatcher: StreamingPrivilegesEventDispatcher,
    ) =
        OnWebSocketMessage(
            networkInteractionsHandler,
            connectRunnable,
            incomingWebSocketMessageParser,
            streamingPrivilegesEventDispatcher,
        )

    @Provides
    @Reusable
    fun onWebSocketOpen(
        connectedFactory: SocketConnectionState.Connected.Factory,
        streamingPrivilegesEventDispatcher: StreamingPrivilegesEventDispatcher,
    ) = OnWebSocketOpen(connectedFactory, streamingPrivilegesEventDispatcher)
}
