package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import androidx.annotation.Px
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.StreamType

sealed class PlaybackStatistics<T : PlaybackStatistics.Payload> :
    StreamingMetrics<T>("playback_statistics") {

    abstract override val payload: T

    @SuppressWarnings("UnusedPrivateMember")
    sealed interface Payload : StreamingMetrics.Payload {

        val idealStartTimestamp: Long
        val actualStartTimestamp: Long?
        val hasAds: Boolean?
        val productType: ProductType
        val actualProductId: String?
        val actualStreamType: StreamType?
        val actualAssetPresentation: AssetPresentation?
        val actualAudioMode: AudioMode?
        val actualQuality: ProductQuality?
        val mediaStorage: MediaStorage?
        val cdm: Cdm?
        val cdmVersion: String?
        val stalls: List<Stall>?
        val adaptations: List<Adaptation>?
        val endTimestamp: Long
        val endReason: EndReason
        val errorMessage: String?
        val errorCode: String?

        @Keep
        enum class Cdm {

            WIDEVINE,
            NONE,
        }

        @Keep
        data class Stall(
            private val reason: Reason,
            @SerializedName("assetPosition") private val assetPositionSeconds: Double,
            private val startTimestamp: Long,
            private val endTimestamp: Long,
        ) {

            @Keep
            enum class Reason {

                SEEK,
                UNEXPECTED,
            }
        }

        @Keep
        data class Adaptation(
            @SerializedName("assetPosition") private val assetPositionSeconds: Double,
            private val timestamp: Long,
            private val mimeType: String,
            private val codecs: String,
            @SerializedName("bandwidth") private val bandwidthBps: Int,
            @Px private val videoWidth: Int,
            @Px private val videoHeight: Int,
        )
    }
}
