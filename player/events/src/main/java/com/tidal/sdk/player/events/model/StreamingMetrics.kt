package com.tidal.sdk.player.events.model

sealed class StreamingMetrics<T : StreamingMetrics.Payload>(name: String) :
    Event<T>(name = name, group = Group.STREAMING_METRICS, version = Version(2)) {

    abstract override val payload: T

    sealed interface Payload : Event.Payload {

        val streamingSessionId: String
    }
}
