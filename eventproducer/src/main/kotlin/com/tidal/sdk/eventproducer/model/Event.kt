package com.tidal.sdk.eventproducer.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Event(
    val id: String,
    val name: String,
    val headers: Map<String, String>,
    val payload: String,
)
