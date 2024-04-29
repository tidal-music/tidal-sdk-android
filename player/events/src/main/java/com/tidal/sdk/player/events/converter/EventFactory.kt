package com.tidal.sdk.player.events.converter

import com.tidal.sdk.player.events.model.Event

internal interface EventFactory<T : Event.Payload> : (T) -> Event<out Event.Payload>
