package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty

internal val PlaybackReport.Handler.Companion.reflectionKEY_PLAYBACK_REPORT: String
    get() = reflectionGetInstanceMemberProperty("KEY_PLAYBACK_REPORT")!!

internal val PlaybackReport.Handler.Companion.reflectionKEY_SOURCE_INFO_TYPE: String
    get() = reflectionGetInstanceMemberProperty("KEY_SOURCE_INFO_TYPE")!!

internal val PlaybackReport.Handler.Companion.reflectionKEY_SOURCE_INFO_ID: String
    get() = reflectionGetInstanceMemberProperty("KEY_SOURCE_INFO_ID")!!
