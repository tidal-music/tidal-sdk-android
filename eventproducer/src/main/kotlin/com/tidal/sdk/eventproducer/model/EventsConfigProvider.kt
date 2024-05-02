package com.tidal.sdk.eventproducer.model

import javax.inject.Singleton

@Singleton
internal class EventsConfigProvider(
    var config: EventsConfig,
) {
    fun updateBlockedConsentCategories(blockedConsentCategories: Set<ConsentCategory>) {
        config = config.copy(blockedConsentCategories = blockedConsentCategories)
    }
}
