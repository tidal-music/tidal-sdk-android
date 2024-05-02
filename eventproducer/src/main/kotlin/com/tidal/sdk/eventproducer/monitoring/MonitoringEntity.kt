package com.tidal.sdk.eventproducer.monitoring

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monitoring")
internal data class MonitoringEntity(
    @PrimaryKey val id: String = MONITORING_ENTITY_PRIMARY_KEY,
    var consentFilteredEvents: Map<String, Int>,
    var validationFailedEvents: Map<String, Int>,
    var storingFailedEvents: Map<String, Int>,
) {
    companion object {
        const val MONITORING_ENTITY_PRIMARY_KEY = "1"
    }
}
