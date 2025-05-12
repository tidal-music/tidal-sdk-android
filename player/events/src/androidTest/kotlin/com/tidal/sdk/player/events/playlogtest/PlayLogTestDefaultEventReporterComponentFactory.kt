package com.tidal.sdk.player.events.playlogtest

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.di.DaggerDefaultEventReporterComponent
import com.tidal.sdk.player.events.di.DefaultEventReporterComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient

class PlayLogTestDefaultEventReporterComponentFactory(
    private val coroutineScopeOverride: CoroutineScope
) : DefaultEventReporterComponent.Factory {
    override fun create(
        context: Context,
        connectivityManager: ConnectivityManager,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        okHttpClient: OkHttpClient,
        gson: Gson,
        uuidWrapper: UUIDWrapper,
        trueTimeWrapper: TrueTimeWrapper,
        eventSender: EventSender,
        coroutineScope: CoroutineScope,
    ) =
        DaggerDefaultEventReporterComponent.factory()
            .create(
                context,
                connectivityManager,
                userSupplier,
                clientSupplier,
                okHttpClient,
                gson,
                uuidWrapper,
                trueTimeWrapper,
                eventSender,
                coroutineScopeOverride,
            )
}
