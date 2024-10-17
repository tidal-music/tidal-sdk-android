package com.tidal.sdk.player.events

import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.events.model.Event

/**
 * Entry point to report events.
 */
interface EventReporter {

    fun <T : Event.Payload> report(payload: T, extras: Extras?)
}
