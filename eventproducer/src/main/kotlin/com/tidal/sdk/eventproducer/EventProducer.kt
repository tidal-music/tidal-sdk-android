package com.tidal.sdk.eventproducer

import android.content.Context
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.di.DaggerEventsComponent
import com.tidal.sdk.eventproducer.model.EventsConfig
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import com.tidal.sdk.eventproducer.outage.OutageState
import com.tidal.sdk.eventproducer.scheduler.MonitoringScheduler
import com.tidal.sdk.eventproducer.scheduler.SendEventBatchScheduler
import java.net.URL
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The [EventProducer] exposes an access to an [EventSender] responsible for sending events.
 * Additionally, it keeps track of the transportation layer's status through the exposure
 * of the [outageState]
 */
class EventProducer private constructor(coroutineScope: CoroutineScope) {

    private var _outageState: MutableStateFlow<OutageState> =
        MutableStateFlow(OutageState.NoOutage())

    val outageState: StateFlow<OutageState> = _outageState.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        OutageState.NoOutage(),
    )

    @Inject
    lateinit var eventSender: EventSender

    @Inject
    internal lateinit var scheduler: SendEventBatchScheduler

    @Inject
    internal lateinit var monitoringScheduler: MonitoringScheduler

    internal fun startOutage() {
        _outageState.value = OutageState.Outage()
    }

    internal fun endOutage() {
        _outageState.value = OutageState.NoOutage()
    }
    companion object {

        @Volatile
        var instance: EventProducer? = null

        /**
         * Used to get a Singleton instance of the [EventProducer]
         *
         * @param tlConsumerUrl identifies the TL Consumer ingest endpoint.
         * TL Consumer is the backend part of the Event delivery platform.
         * @param credentialsProvider is an implementation of the [CredentialsProvider] from the
         * tidal sdk auth module. It's responsible for providing credentials.
         * @param config specifies basic attributes of the Event Producer contained within the
         * [EventsConfig].
         * @param context is a context.
         * @param coroutineScope used to launch coroutines responsible for adding events to
         * local db and scheduling sending events to the TL consumer.
         * @return EventProducer instance.
         */
        fun getInstance(
            tlConsumerUrl: URL,
            credentialsProvider: CredentialsProvider,
            config: EventsConfig,
            context: Context,
            coroutineScope: CoroutineScope,
        ): EventProducer {
            return instance ?: synchronized(this) {
                val eventsComponent =
                    DaggerEventsComponent.factory()
                        .create(
                            context.applicationContext,
                            coroutineScope,
                            credentialsProvider,
                            EventsConfigProvider(config),
                            tlConsumerUrl,
                        )
                EventProducer(coroutineScope).also {
                    eventsComponent.inject(it)
                    coroutineScope.launch(Dispatchers.IO) { it.scheduler.scheduleBatchAndSend() }
                    coroutineScope.launch(Dispatchers.IO) {
                        it.monitoringScheduler.scheduleSendMonitoringInfo()
                    }
                    instance = it
                }
            }
        }
    }
}
