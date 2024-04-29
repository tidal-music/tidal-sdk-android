package com.tidal.sdk.player.playbackengine.dj

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetInstanceMemberProperty

internal var DjSessionManager.reflectionCurrentDateRange: DateRange?
    get() = reflectionGetInstanceMemberProperty("currentDateRange")
    set(value) = reflectionSetInstanceMemberProperty("currentDateRange", value)
