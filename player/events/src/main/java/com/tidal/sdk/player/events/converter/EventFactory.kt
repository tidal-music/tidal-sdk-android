package com.tidal.sdk.player.events.converter

import com.tidal.sdk.player.events.model.Event

internal interface EventFactory<T : Event.Payload> {
    suspend operator fun invoke(payload: T, extras: Map<String, String?>?): Event<out Event.Payload>
}
