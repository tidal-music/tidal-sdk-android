package com.tidal.sdk.player.playbackengine.dj

data class DateRange(
    val id: String,
    val clazz: String,
    val startDate: String,
    val productId: String,
    val status: DjSessionStatus,
    val endOnNext: String,
)
