package com.tidal.sdk.eventproducer.di

import android.content.Context
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.EventProducer
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import dagger.BindsInstance
import dagger.Component
import java.net.URI
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope

@Singleton
@Component(
    modules =
        [
            DatabaseModule::class,
            EventProducerModule::class,
            NetworkModule::class,
            OkHttpModule::class,
            ConvertersModule::class,
            UtilsModule::class,
        ]
)
internal interface EventsComponent {

    fun inject(eventsSender: EventProducer)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance coroutineScope: CoroutineScope,
            @BindsInstance credentialsProvider: CredentialsProvider,
            @BindsInstance configProvider: EventsConfigProvider,
            @BindsInstance tlConsumerUri: URI,
        ): EventsComponent
    }
}
