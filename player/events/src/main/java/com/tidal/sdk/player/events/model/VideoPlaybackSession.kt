package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.VideoQuality
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
data class VideoPlaybackSession
@AssistedInject
constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
    @Assisted override val extras: Extras?,
) : PlaybackSession<VideoPlaybackSession.Payload>() {

    @Keep
    data class Payload(
        override val playbackSessionId: UUID,
        override val startTimestamp: Long,
        @SerializedName("startAssetPosition") override val startAssetPositionSeconds: Double,
        override val requestedProductId: String,
        override val actualProductId: String,
        override val actualAssetPresentation: AssetPresentation,
        override val actualQuality: VideoQuality,
        override val sourceType: String?,
        override val sourceId: String?,
        override val actions: List<PlaybackSession.Payload.Action>,
        override val endTimestamp: Long,
        @SerializedName("endAssetPosition") override val endAssetPositionSeconds: Double,
    ) : PlaybackSession.Payload {

        override val productType = ProductType.VIDEO
        override val isPostPaywall = true
        override val actualAudioMode = null
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
        ): VideoPlaybackSession
    }
}
