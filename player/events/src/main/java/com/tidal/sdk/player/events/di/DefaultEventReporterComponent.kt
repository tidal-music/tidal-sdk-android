package com.tidal.sdk.player.events.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.UserSupplier
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient

@Component(
    modules = [
        DefaultEventReporterModule::class,
        EventFactoryModule::class,
    ],
)
@Singleton
interface DefaultEventReporterComponent {

    val eventReporter: EventReporter

    @Component.Factory
    interface Factory {
        @Suppress("LongParameterList")
        fun create(
            @BindsInstance context: Context,
            @BindsInstance connectivityManager: ConnectivityManager,
            @BindsInstance userSupplier: UserSupplier,
            @BindsInstance clientSupplier: ClientSupplier,
            @BindsInstance okHttpClient: OkHttpClient,
            @BindsInstance gson: Gson,
            @BindsInstance uuidWrapper: UUIDWrapper,
            @BindsInstance trueTimeWrapper: TrueTimeWrapper,
            @BindsInstance eventSender: EventSender,
            @BindsInstance coroutineScope: CoroutineScope,
        ): DefaultEventReporterComponent
    }
}
