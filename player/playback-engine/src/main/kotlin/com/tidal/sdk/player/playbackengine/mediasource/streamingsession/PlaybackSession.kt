package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.events.model.PlaybackSession.Payload.Action
import java.util.UUID
import kotlin.properties.Delegates

internal sealed class PlaybackSession {

    abstract val playbackSessionId: UUID
    abstract val actualProductId: String
    abstract val requestedProductId: String
    abstract val actualQuality: ProductQuality
    abstract val sourceType: String?
    abstract val sourceId: String?
    val actions: List<Action> = mutableListOf()

    var startTimestamp = 0L
    var startAssetPosition by
        Delegates.vetoable(START_ASSET_POSITION_UNASSIGNED) { _, oldValue, newValue ->
            oldValue == START_ASSET_POSITION_UNASSIGNED && newValue >= 0
        }

    fun tryAddAction(action: Action) {
        if (actions.lastOrNull()?.actionType == action.actionType) {
            /**
             * PlayLog-wise, we can't start while started or stop while stopped. However,
             * ExoPlayer-wise, it can happen for example when a discontinuity occurs while paused.
             */
            return
        }
        (actions as MutableList<Action>).add(action)
    }

    @Suppress("LongParameterList")
    class Audio(
        override val playbackSessionId: UUID,
        override val actualProductId: String,
        override val requestedProductId: String,
        val actualAssetPresentation: AssetPresentation,
        val actualAudioMode: AudioMode,
        override val actualQuality: AudioQuality,
        override val sourceType: String?,
        override val sourceId: String?,
    ) : PlaybackSession()

    @Suppress("LongParameterList")
    class Video(
        override val playbackSessionId: UUID,
        override val actualProductId: String,
        override val requestedProductId: String,
        val actualAssetPresentation: AssetPresentation,
        override val actualQuality: VideoQuality,
        override val sourceType: String?,
        override val sourceId: String?,
    ) : PlaybackSession()

    class Broadcast(
        override val playbackSessionId: UUID,
        override val actualProductId: String,
        override val requestedProductId: String,
        override val actualQuality: AudioQuality,
        override val sourceType: String?,
        override val sourceId: String?,
    ) : PlaybackSession()

    @Suppress("LongParameterList")
    class UC(
        override val playbackSessionId: UUID,
        override val actualProductId: String,
        override val requestedProductId: String,
        override val sourceType: String?,
        override val sourceId: String?,
    ) : PlaybackSession() {

        override val actualQuality = AudioQuality.LOW
    }

    companion object {

        private const val START_ASSET_POSITION_UNASSIGNED = -1.0
    }
}
