package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
data class VideoPlaybackStatistics @AssistedInject internal constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
) : PlaybackStatistics<VideoPlaybackStatistics.Payload>() {

    @Keep
    data class Payload(
        override val streamingSessionId: String,
        override val idealStartTimestamp: Long,
        override val actualStartTimestamp: Long?,
        override val actualProductId: String,
        override val actualStreamType: StreamType,
        override val actualAssetPresentation: AssetPresentation,
        override val actualQuality: VideoQuality,
        override val mediaStorage: MediaStorage,
        override val cdm: PlaybackStatistics.Payload.Cdm,
        override val cdmVersion: String?,
        override val stalls: List<PlaybackStatistics.Payload.Stall>,
        override val adaptations: List<PlaybackStatistics.Payload.Adaptation>,
        override val endTimestamp: Long,
        override val endReason: EndReason,
        override val errorMessage: String?,
        override val errorCode: String?,
    ) : PlaybackStatistics.Payload {

        override val productType = ProductType.VIDEO
        override val hasAds = false // TODO Send actual value
        override val actualAudioMode = null
    }

    @AssistedFactory
    internal interface Factory {

        fun create(ts: Long, uuid: UUID, user: User, client: Client, payload: Payload):
            VideoPlaybackStatistics
    }
}
