package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.common.model.ProductType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
data class UCPlaybackSession
@AssistedInject
internal constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
    @Assisted override val extras: Extras?,
) : PlaybackSession<UCPlaybackSession.Payload>() {

    @Keep
    data class Payload(
        override val playbackSessionId: UUID,
        override val startTimestamp: Long,
        @SerializedName("startAssetPosition") override val startAssetPositionSeconds: Double,
        override val requestedProductId: String,
        override val actualProductId: String,
        override val actualQuality: AudioQuality,
        override val sourceType: String?,
        override val sourceId: String?,
        override val actions: List<PlaybackSession.Payload.Action>,
        override val endTimestamp: Long,
        @SerializedName("endAssetPosition") override val endAssetPositionSeconds: Double,
    ) : PlaybackSession.Payload {

        override val actualAssetPresentation = AssetPresentation.FULL
        override val actualAudioMode = AudioMode.STEREO
        override val productType = ProductType.UC
        override val isPostPaywall = true
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
        ): UCPlaybackSession
    }
}
