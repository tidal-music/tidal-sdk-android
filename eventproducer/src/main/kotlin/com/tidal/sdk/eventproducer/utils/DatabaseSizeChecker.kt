package com.tidal.sdk.eventproducer.utils

import com.tidal.sdk.eventproducer.database.EventsDatabase

/**
 * This class is an entry point to evaluate if local database size limit has been reached
 *
 * @property db is local database
 * @property maxDiskUsageBytes specifies the maximum amount of disk the EventProducer is allowed to
 *   use for temporarily storing events before they are sent to TL Consumer.
 */
internal class DatabaseSizeChecker(
    private val db: EventsDatabase,
    private val maxDiskUsageBytes: Int,
) {

    fun isDatabaseLimitReached(): Boolean = db.isDatabaseLimitReached(maxDiskUsageBytes)
}
