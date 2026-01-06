package com.tidal.sdk.player.events.model

import androidx.annotation.Keep
import androidx.annotation.Px
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.common.model.ProductType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

@Keep
@Suppress("UnusedPrivateMember")
data class StreamingSessionStart
@AssistedInject
constructor(
    @Assisted override val ts: Long,
    @Assisted override val uuid: UUID,
    @Assisted override val user: User,
    @Assisted override val client: Client,
    @Assisted override val payload: DecoratedPayload,
    @Assisted override val extras: Extras?,
) : StreamingMetrics<StreamingSessionStart.DecoratedPayload>(name = "streaming_session_start") {

    data class Payload(
        override val streamingSessionId: String,
        val timestamp: Long,
        val startReason: StartReason,
        val isOfflineModeStart: Boolean,
        val sessionProductType: ProductType,
        val sessionProductId: String,
    ) : StreamingMetrics.Payload {

        val sessionType = SessionType.PLAYBACK
    }

    @Keep
    data class DecoratedPayload
    @AssistedInject
    constructor(
        @Assisted("streamingSessionId") override val streamingSessionId: String,
        @Assisted private val timestamp: Long,
        @Assisted("isOfflineModeStart") private val isOfflineModeStart: Boolean,
        @Assisted private val startReason: StartReason,
        @Assisted("hardwarePlatform") private val hardwarePlatform: String,
        @Assisted("operatingSystem") private val operatingSystem: String,
        @Assisted("operatingSystemVersion") private val operatingSystemVersion: String,
        @Assisted("screenWidth") @Px private val screenWidth: Int,
        @Assisted("screenHeight") @Px private val screenHeight: Int,
        @Assisted private val networkType: NetworkType,
        @Assisted("mobileNetworkType") private val mobileNetworkType: String,
        @Assisted private val sessionType: SessionType,
        @Assisted private val sessionProductType: ProductType,
        @Assisted("sessionProductId") private val sessionProductId: String,
    ) : StreamingMetrics.Payload {

        @AssistedFactory
        internal interface Factory {

            @Suppress("LongParameterList")
            fun create(
                @Assisted("streamingSessionId") streamingSessionId: String,
                timestamp: Long,
                @Assisted("isOfflineModeStart") isOfflineModeStart: Boolean,
                startReason: StartReason,
                @Assisted("hardwarePlatform") hardwarePlatform: String,
                @Assisted("operatingSystem") operatingSystem: String,
                @Assisted("operatingSystemVersion") operatingSystemVersion: String,
                @Px @Assisted("screenWidth") screenWidth: Int,
                @Px @Assisted("screenHeight") screenHeight: Int,
                networkType: NetworkType,
                @Assisted("mobileNetworkType") mobileNetworkType: String,
                sessionType: SessionType,
                sessionProductType: ProductType,
                @Assisted("sessionProductId") sessionProductId: String,
            ): DecoratedPayload
        }
    }

    @Keep
    enum class StartReason {

        EXPLICIT,
        IMPLICIT,
    }

    @Keep
    enum class SessionType {

        PLAYBACK
    }

    @Keep
    internal enum class NetworkType {

        ETHERNET,
        WIFI,
        MOBILE,
        OTHER,
        NONE,
    }

    @AssistedFactory
    internal interface Factory {

        @Suppress("LongParameterList")
        fun create(
            ts: Long,
            uuid: UUID,
            user: User,
            client: Client,
            decoratedPayload: DecoratedPayload,
            extras: Extras?,
        ): StreamingSessionStart
    }
}
