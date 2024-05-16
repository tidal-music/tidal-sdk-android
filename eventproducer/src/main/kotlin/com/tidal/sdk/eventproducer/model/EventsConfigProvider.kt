package com.tidal.sdk.eventproducer.model

import javax.inject.Singleton

@Singleton
class EventsConfigProvider(
    var config: EventsConfig,
) {
    fun updateBlacklistedConsentCategories(blacklistedConsentCategories: Set<ConsentCategory>) {
        config = config.copy(blacklistedConsentCategories = blacklistedConsentCategories)
    }
}
