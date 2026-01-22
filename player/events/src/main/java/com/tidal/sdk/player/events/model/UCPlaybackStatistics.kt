package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.ProductType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
data class UCPlaybackStatistics
@AssistedInject
constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
    @Assisted override val extras: Extras?,
) : PlaybackStatistics<UCPlaybackStatistics.Payload>() {

    @Keep
    data class Payload(
        override val streamingSessionId: String,
        override val idealStartTimestamp: Long,
        override val actualStartTimestamp: Long?,
        override val actualProductId: String,
        override val actualQuality: AudioQuality,
        override val mediaStorage: MediaStorage,
        override val cdm: PlaybackStatistics.Payload.Cdm,
        override val cdmVersion: String?,
        override val stalls: List<PlaybackStatistics.Payload.Stall>,
        override val adaptations: List<PlaybackStatistics.Payload.Adaptation>,
        override val endTimestamp: Long,
        override val endReason: EndReason,
        override val errorMessage: String?,
        override val errorCode: String?,
        override val tags: List<String> = emptyList(),
    ) : PlaybackStatistics.Payload {

        override val actualAssetPresentation = AssetPresentation.FULL
        override val actualAudioMode = AudioMode.STEREO
        override val productType = ProductType.UC
        override val hasAds = false // TODO Send actual value
        override val actualStreamType = null
    }

    @AssistedFactory
    internal interface Factory {

        @Suppress("LongParameterList")
        fun create(
            ts: Long,
            uuid: UUID,
            user: User,
            client: Client,
            payload: Payload,
            extras: Extras?,
        ): UCPlaybackStatistics
    }
}
