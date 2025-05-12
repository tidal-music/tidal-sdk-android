package com.tidal.sdk.eventproducer.model

/**
 * @property maxDiskUsageBytes specifies the maximum amount of disk the EventProducer is allowed to
 *   use for temporarily storing events before they are sent to TL Consume
 * @property blockedConsentCategories specifies consent categories based on which events will be
 *   filtered
 * @property appVersion specifies version of the app
 */
data class EventsConfig(
    val maxDiskUsageBytes: Int,
    val blockedConsentCategories: Set<ConsentCategory>,
    val appVersion: String,
)
