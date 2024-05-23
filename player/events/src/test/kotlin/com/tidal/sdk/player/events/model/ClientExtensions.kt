package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty

internal val Client.reflectionToken: String
    get() = reflectionGetInstanceMemberProperty("token")!!

internal val Client.reflectionDeviceType: Client.DeviceType
    get() = reflectionGetInstanceMemberProperty("deviceType")!!

internal val Client.reflectionVersion: String
    get() = reflectionGetInstanceMemberProperty("version")!!
