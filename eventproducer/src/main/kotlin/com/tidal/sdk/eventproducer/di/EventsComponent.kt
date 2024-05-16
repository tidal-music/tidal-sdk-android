package com.tidal.sdk.eventproducer.di

import android.content.Context
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.auth.AuthProvider
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import dagger.BindsInstance
import dagger.Component
import java.net.URL
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class,
        OkHttpModule::class,
        ConvertersModule::class,
        UtilsModule::class,
    ],
)
internal interface EventsComponent {

    fun inject(eventsSender: EventSender)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance authProvider: AuthProvider,
            @BindsInstance configProvider: EventsConfigProvider,
            @BindsInstance tlConsumerUrl: URL,
        ): EventsComponent
    }
}
