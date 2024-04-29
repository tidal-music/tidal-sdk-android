package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty

internal val User.reflectionId: Long
    get() = reflectionGetInstanceMemberProperty("id")!!

internal val User.reflectionSessionId: String
    get() = reflectionGetInstanceMemberProperty("sessionId")!!
