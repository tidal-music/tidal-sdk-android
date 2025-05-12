package com.tidal.sdk.player.events.model

sealed class PlayLog<T : Event.Payload>(name: String) :
    Event<T>(name = name, group = Group.PLAY_LOG, version = Version(2))
