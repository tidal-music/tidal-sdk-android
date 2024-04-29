package com.tidal.sdk.player.playbackengine.dj

import android.os.Handler

internal class DjSessionManager(
    private val hlsTagsParser: HlsTagsParser,
    private val dateParser: DateParser,
    private val handler: Handler,
) {

    private var currentDateRange: DateRange? = null

    var listener: Listener? = null

    fun cleanUp() {
        this.listener = null
        handler.removeCallbacksAndMessages(null)
        currentDateRange = null
    }

    fun checkForUpdates(tags: List<String>, currentPositionSinceEpochMs: Long) {
        val dateRanges = hlsTagsParser.parse(tags)

        if (dateRanges.isEmpty()) {
            return
        }

        val dateRange = dateRanges[0]
        val startDateSinceEpochMs = dateParser.parseXsDateTime(dateRange.startDate)
        if (dateRanges.size == 1 && currentPositionSinceEpochMs >= startDateSinceEpochMs) {
            update(dateRange)
        } else if (dateRanges.size >= 2) {
            val dateRangeNext = dateRanges[1]
            val startDateSinceEpochMsNext = dateParser.parseXsDateTime(dateRangeNext.startDate)
            if (currentPositionSinceEpochMs < startDateSinceEpochMsNext) {
                update(dateRange)
                val msUntilProductUpdate = startDateSinceEpochMsNext - currentPositionSinceEpochMs
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({ update(dateRangeNext) }, msUntilProductUpdate)
            } else {
                update(dateRangeNext)
            }
        }
    }

    private fun update(dateRange: DateRange) {
        if (currentDateRange != dateRange) {
            currentDateRange = dateRange
            listener?.onDjSessionUpdated(dateRange.productId, dateRange.status)
        }
    }

    interface Listener {
        fun onDjSessionUpdated(productId: String, status: DjSessionStatus)
    }
}
