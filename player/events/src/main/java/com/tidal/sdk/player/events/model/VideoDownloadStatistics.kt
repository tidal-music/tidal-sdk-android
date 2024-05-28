package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.VideoQuality
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
internal data class VideoDownloadStatistics @AssistedInject constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: Payload,
) : DownloadStatistics<VideoDownloadStatistics.Payload>() {

    @Keep
    data class Payload(
        override val streamingSessionId: String,
        override val startTimestamp: Long,
        override val actualProductId: String,
        override val actualAssetPresentation: AssetPresentation,
        override val actualAudioMode: AudioMode,
        override val actualQuality: VideoQuality,
        override val endReason: EndReason,
        override val endTimestamp: Long,
        override val errorMessage: String?,
        override val errorCode: String?,
    ) : DownloadStatistics.Payload {

        override val productType = ProductType.VIDEO
    }

    @AssistedFactory
    interface Factory {

        fun create(
            ts: Long,
            uuid: UUID,
            user: User,
            client: Client,
            payload: Payload,
        ): VideoDownloadStatistics
    }
}
