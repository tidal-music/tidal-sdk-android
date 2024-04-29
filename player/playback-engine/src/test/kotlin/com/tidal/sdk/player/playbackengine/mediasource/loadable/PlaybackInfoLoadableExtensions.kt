package com.tidal.sdk.player.playbackengine.mediasource.loadable

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import kotlinx.coroutines.CoroutineScope

internal val PlaybackInfoLoadable.reflectionCoroutineScope: CoroutineScope
    get() = reflectionGetInstanceMemberProperty("coroutineScope")!!
