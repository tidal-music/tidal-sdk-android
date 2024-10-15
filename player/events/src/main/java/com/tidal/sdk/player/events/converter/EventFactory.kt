package com.tidal.sdk.player.events.converter

import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.events.model.Event

internal interface EventFactory<T : Event.Payload> {
    suspend operator fun invoke(payload: T, extras: Extras?): Event<out Event.Payload>
}
