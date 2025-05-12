package com.tidal.sdk.eventproducer

import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import com.tidal.sdk.eventproducer.monitoring.MonitoringInfo
import com.tidal.sdk.eventproducer.utils.CoroutineScopeCanceledException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Singleton
internal class DefaultEventSender
@Inject
constructor(
    private val coroutineScope: CoroutineScope,
    private val submitter: Submitter,
    private val configProvider: EventsConfigProvider,
) : EventSender {

    /**
     * Sends an event to the TL Consumer. Tl Consumer - backend part of the event delivery platform
     *
     * @param eventName The name of the event
     * @param consentCategory The consent category the event belongs to
     * @param payload The payload of the event, i.e. the actual business data being sent.
     * @param headers Optional headers the app want to send together with the event.
     * @throws [CoroutineScopeCanceledException] if the provided coroutine scope has been canceled
     */
    override fun sendEvent(
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

    /**
     * Sets blocked consent categories which results in filtering events associated with the blocked
     * category. Events that are dropped are included in the monitoring statistics in the
     * [MonitoringInfo].
     */
    override fun setBlockedConsentCategories(blockedConsentCategories: Set<ConsentCategory>) {
        configProvider.updateBlockedConsentCategories(blockedConsentCategories)
    }
}
