package com.tidal.sdk.player.di

import android.app.UiModeManager
import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.commonandroid.jwt.Base64JwtDecoder
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.EventReporterModuleRoot
import com.tidal.sdk.player.events.UserSupplier
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient

@Module
internal object EventReporterModule {

    @Provides
    @Reusable
    fun base64JwtDecoder(base64Codec: Base64Codec) = Base64JwtDecoder(base64Codec)

    @Provides
    @Singleton
    fun userSupplier(
        base64JwtDecoder: Base64JwtDecoder,
        credentialsProvider: CredentialsProvider,
        userClientIdSupplier: (() -> Int)?,
    ) = UserSupplier(base64JwtDecoder, credentialsProvider, userClientIdSupplier)

    @Provides
    @Reusable
    fun uiModeManager(context: Context): UiModeManager =
        context.getSystemService(UiModeManager::class.java)

    @Provides
    @Singleton
    fun clientSupplier(
        context: Context,
        uiModeManager: UiModeManager,
        base64JwtDecoder: Base64JwtDecoder,
        credentialsProvider: CredentialsProvider,
        version: String,
    ) = ClientSupplier(context, uiModeManager, base64JwtDecoder, credentialsProvider, version)

    @Provides @Reusable fun coroutineDispatcher() = Dispatchers.Default

    @Provides
    @Singleton
    fun coroutineScope(coroutineDispatcher: CoroutineDispatcher) =
        CoroutineScope(coroutineDispatcher)

    @Provides
    @Singleton
    fun eventReporter(
        context: Context,
        connectivityManager: ConnectivityManager,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        @LocalWithCacheAndAuth okHttpClient: OkHttpClient,
        gson: Gson,
        uuidWrapper: UUIDWrapper,
        trueTimeWrapper: TrueTimeWrapper,
        eventSender: EventSender,
        coroutineScope: CoroutineScope,
    ) =
        EventReporterModuleRoot(
                context,
                connectivityManager,
                userSupplier,
                clientSupplier,
                okHttpClient,
                gson,
                uuidWrapper,
                trueTimeWrapper,
                eventSender,
                coroutineScope,
            )
            .eventReporter
}
