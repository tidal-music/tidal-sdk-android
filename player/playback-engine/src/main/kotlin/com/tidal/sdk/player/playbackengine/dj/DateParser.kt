package com.tidal.sdk.player.playbackengine.dj

import androidx.media3.common.util.Util

internal class DateParser {
    @Suppress("UnsafeOptInUsageError")
    fun parseXsDateTime(value: String) = Util.parseXsDateTime(value)
}
