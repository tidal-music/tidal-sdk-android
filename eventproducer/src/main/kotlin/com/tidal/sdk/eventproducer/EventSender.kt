package com.tidal.sdk.eventproducer

import com.tidal.sdk.eventproducer.model.ConsentCategory

interface EventSender {

    fun sendEvent(
        eventName: String,
        consentCategory: ConsentCategory,
        payload: String,
        headers: Map<String, String>,
    )

    fun setBlockedConsentCategories(blockedConsentCategories: Set<ConsentCategory>)
}
