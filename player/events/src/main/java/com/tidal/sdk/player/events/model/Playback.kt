package com.tidal.sdk.player.events.model

sealed class Playback(name: String) : Event<Event.Payload>(
    name = name,
    group = Group.PLAYBACK,
    version = Version(1),
)
