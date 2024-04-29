package com.tidal.sdk.player.playbackengine.mediasource.loadable

import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.StreamingApiRepository
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

internal class PlaybackInfoLoadableFactory(
    private val streamingApiRepository: StreamingApiRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val extendedExoPlayerState: ExtendedExoPlayerState,
    private val playbackPrivilegeProvider: PlaybackPrivilegeProvider,
) {

    fun create(
        streamingSession: StreamingSession,
        forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>,
    ) =
        PlaybackInfoLoadable(
            streamingSession,
            forwardingMediaProduct,
            streamingApiRepository,
            extendedExoPlayerState,
            playbackPrivilegeProvider,
        ) { CoroutineScope(coroutineDispatcher) }
}
