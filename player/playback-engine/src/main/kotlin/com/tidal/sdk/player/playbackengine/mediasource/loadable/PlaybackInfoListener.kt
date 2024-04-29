package com.tidal.sdk.player.playbackengine.mediasource.loadable

import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/**
 * Receive callbacks when a playback info has been fetched for a particular
 * [ForwardingMediaProduct].
 */
internal interface PlaybackInfoListener {
    fun onPlaybackInfoFetched(
        streamingSession: StreamingSession,
        forwardingMediaProduct: ForwardingMediaProduct<*>,
        playbackInfo: PlaybackInfo,
    )
}
