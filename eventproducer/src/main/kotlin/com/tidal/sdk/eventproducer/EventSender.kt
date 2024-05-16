package com.tidal.sdk.eventproducer

import android.content.Context
import com.tidal.sdk.eventproducer.auth.AuthProvider
import com.tidal.sdk.eventproducer.di.DaggerEventsComponent
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.eventproducer.model.EventsConfig
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import com.tidal.sdk.eventproducer.outage.OutageState
import com.tidal.sdk.eventproducer.scheduler.MonitoringScheduler
import com.tidal.sdk.eventproducer.scheduler.SendEventBatchScheduler
import com.tidal.sdk.eventproducer.utils.CoroutineScopeCanceledException
import java.net.URL
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * The EventSender role exposes functionality for sending events and monitoring the status of the
 * transportation layer.
 */
class EventSender private constructor(
    private val coroutineScope: CoroutineScope,
) {

    private var _outageState: MutableStateFlow<OutageState> =
        MutableStateFlow(OutageState.NoOutage())

    val outageState: StateFlow<OutageState> = _outageState.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        OutageState.NoOutage(),
    )

    @Inject
    internal lateinit var submitter: Submitter

    @Inject
    internal lateinit var scheduler: SendEventBatchScheduler

    @Inject
    internal lateinit var monitoringScheduler: MonitoringScheduler

    @Inject
    internal lateinit var configProvider: EventsConfigProvider

    /**
     * Sends an event to the TL Consumer. Tl Consumer - backend part of the event delivery platform
     * @param eventName The name of the event
     * @param consentCategory The consent category the event belongs to
     * @param payload The payload of the event, i.e. the actual business data being sent.
     * @param headers Optional headers the app want to send together with the event.
     * @throws [CoroutineScopeCanceledException] if the provided coroutine scope has been canceled
     */
    fun sendEvent(
        eventName: String,
        consentCategory: ConsentCategory,
        payload: String,
        headers: Map<String, String>,
    ) {
        if (coroutineScope.isActive) {
            coroutineScope.launch(Dispatchers.IO) {
                submitter.sendEvent(eventName, consentCategory, payload, headers)
            }
        } else {
            throw CoroutineScopeCanceledException()
        }
    }

    fun setBlacklistedConsentCategories(blacklistedConsentCategories: Set<ConsentCategory>) {
        configProvider.updateBlacklistedConsentCategories(blacklistedConsentCategories)
    }

    internal fun startOutage() {
        _outageState.value = OutageState.Outage()
    }

    internal fun endOutage() {
        _outageState.value = OutageState.NoOutage()
    }

    companion object {

        @Volatile
        var instance: EventSender? = null

        /**
         * Used to get a Singleton instance of the [EventSender]
         *
         * @param tlConsumerUrl identifies the TL Consumer ingest endpoint.
         * TL Consumer is the backend part of the Event delivery platform
         * @param authProvider authorization provider, used by the EventProducer to get
         * access token and client id .
         * @param config specifies basic Event Producer attributes included in [EventsConfig]
         * @param context is the app context.
         * @param coroutineScope used to launch coroutines responsible for adding events to
         * local db and scheduling sending events to the TL consumer.
         * @return EventSender instance.
         */
        fun getInstance(
            tlConsumerUrl: URL,
            authProvider: AuthProvider,
            config: EventsConfig,
            context: Context,
            coroutineScope: CoroutineScope,
        ): EventSender {
            return instance ?: synchronized(this) {
                val eventsComponent =
                    DaggerEventsComponent.factory()
                        .create(
                            context.applicationContext,
                            authProvider,
                            EventsConfigProvider(config),
                            tlConsumerUrl,
                        )
                EventSender(coroutineScope).also {
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
