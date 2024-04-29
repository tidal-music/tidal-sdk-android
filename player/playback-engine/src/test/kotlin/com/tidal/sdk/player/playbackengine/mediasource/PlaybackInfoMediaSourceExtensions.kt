package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MediaSourceEventListener.EventDispatcher
import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetInstanceMemberProperty

internal val PlaybackInfoMediaSource.reflectionEventDispatcher: EventDispatcher
    get() = reflectionGetInstanceMemberProperty("eventDispatcher")!!

internal val PlaybackInfoMediaSource.reflectionPrepareChildSourceF: (MediaSource) -> Unit
    get() = reflectionGetInstanceMemberProperty("prepareChildSourceF")!!

internal val PlaybackInfoMediaSource.reflectionPrepareSourceInternalF: () -> Unit
    get() = reflectionGetInstanceMemberProperty("prepareSourceInternalF")!!

internal fun PlaybackInfoMediaSource.reflectionSetCreateEventDispatcherF(
    newEventDispatcherF: () -> EventDispatcher,
) = reflectionSetInstanceMemberProperty("createEventDispatcherF", newEventDispatcherF)
