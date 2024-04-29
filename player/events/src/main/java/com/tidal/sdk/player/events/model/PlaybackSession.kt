package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import java.util.UUID

sealed class PlaybackSession<T : PlaybackSession.Payload> :
    PlayLog<T>(name = "playback_session") {

    abstract override val payload: T

    sealed interface Payload : Event.Payload {

        val playbackSessionId: UUID
        val startTimestamp: Long
        val startAssetPositionSeconds: Double
        val isPostPaywall: Boolean
        val productType: ProductType
        val requestedProductId: String
        val actualProductId: String
        val actualAssetPresentation: AssetPresentation
        val actualAudioMode: AudioMode?
        val actualQuality: ProductQuality
        val sourceType: String?
        val sourceId: String?
        val actions: List<Action>
        val endTimestamp: Long
        val endAssetPositionSeconds: Double

        @Keep
        @SuppressWarnings("UnusedPrivateMember")
        data class Action(
            private val timestamp: Long,
            @SerializedName("assetPosition")
            private val assetPositionSeconds: Double,
            private val actionType: Type,
        ) {

            @Keep
            enum class Type {

                PLAYBACK_START,
                PLAYBACK_STOP,
            }
        }
    }
}
