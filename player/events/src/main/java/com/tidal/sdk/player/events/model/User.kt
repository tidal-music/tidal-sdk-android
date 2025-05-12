package com.tidal.sdk.player.events.model

import androidx.annotation.Keep

/** Information about the user an event is tied to. */
@Keep
@Suppress("UnusedPrivateMember")
data class User(private val id: Long, private val clientId: Int?, private val sessionId: String)
