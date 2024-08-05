package com.tidal.sdk.player.events

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.events.di.DaggerDefaultEventReporterComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient

@Suppress("LongParameterList")
class EventReporterModuleRoot(
    context: Context,
    connectivityManager: ConnectivityManager,
    userSupplier: UserSupplier,
    clientSupplier: ClientSupplier,
    okHttpClient: OkHttpClient,
    gson: Gson,
    uuidWrapper: UUIDWrapper,
    sntpClient: SNTPClient,
    eventSender: EventSender,
    coroutineScope: CoroutineScope,
) {

    val eventReporter = componentFactoryF()
        .create(
            context,
            connectivityManager,
            userSupplier,
            clientSupplier,
            okHttpClient,
            gson,
            uuidWrapper,
            sntpClient,
            eventSender,
            coroutineScope,
        ).eventReporter

    companion object {

        private var componentFactoryF = { DaggerDefaultEventReporterComponent.factory() }
    }
}
