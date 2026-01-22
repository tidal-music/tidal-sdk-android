package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.common.model.ProductType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
data class NotStartedPlaybackStatistics
@AssistedInject
constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
    @Assisted override val extras: Extras?,
) : PlaybackStatistics<NotStartedPlaybackStatistics.Payload>() {

    @Keep
    data class Payload(
        override val streamingSessionId: String,
        override val idealStartTimestamp: Long,
        override val productType: ProductType,
        override val endTimestamp: Long,
        override val errorMessage: String?,
        override val errorCode: String,
        override val endReason: EndReason,
        override val tags: List<String> = emptyList(),
    ) : PlaybackStatistics.Payload {

        override val actualStartTimestamp = null
        override val hasAds = null
        override val actualProductId = null
        override val actualStreamType = null
        override val actualAssetPresentation = null
        override val actualAudioMode = null
        override val actualQuality = null
        override val mediaStorage = null
        override val cdm = null
        override val cdmVersion = null
        override val stalls = null
        override val adaptations = null
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
        ): NotStartedPlaybackStatistics
    }
}
