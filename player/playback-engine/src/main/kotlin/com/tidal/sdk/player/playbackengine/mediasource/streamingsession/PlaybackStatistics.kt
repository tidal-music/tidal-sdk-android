package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.events.model.PlaybackStatistics.Payload.Adaptation
import com.tidal.sdk.player.events.model.PlaybackStatistics.Payload.Stall
import java.util.UUID

internal sealed interface PlaybackStatistics {

    val streamingSessionId: UUID
    val adaptations: List<Adaptation>
    val extras: Map<String, String?>?

    operator fun plus(adaptation: Adaptation): PlaybackStatistics

    val idealStartTimestampMs: IdealStartTimestampMs

    sealed class IdealStartTimestampMs {

        abstract val timestamp: Long

        object NotYetKnown : IdealStartTimestampMs() {

            override val timestamp = -1L
        }

        data class Known(override val timestamp: Long) : IdealStartTimestampMs()
    }

    data class Undetermined(
        override val streamingSessionId: UUID,
        override val idealStartTimestampMs: IdealStartTimestampMs,
        override val adaptations: List<Adaptation>,
        override val extras: Map<String, String?>?,
    ) : PlaybackStatistics {

        override fun plus(adaptation: Adaptation) =
            if (adaptations.size == ADAPTATIONS_MAX) {
                this
            } else {
                copy(adaptations = adaptations + adaptation)
            }
    }

    sealed interface Success : PlaybackStatistics {

        val actualProductId: String
        val versionedCdm: VersionedCdm
        val actualQuality: ProductQuality
        val mediaStorage: MediaStorage

        sealed interface Prepared : Success {

            fun toStarted(actualStartTimestampMs: Long) =
                Started(this, actualStartTimestampMs, emptyList())

            data class Audio(
                override val streamingSessionId: UUID,
                override val actualProductId: String,
                override val idealStartTimestampMs: IdealStartTimestampMs,
                val actualAssetPresentation: AssetPresentation,
                override val versionedCdm: VersionedCdm,
                override val actualQuality: AudioQuality,
                override val adaptations: List<Adaptation>,
                val actualAudioMode: AudioMode,
                override val mediaStorage: MediaStorage,
                override val extras: Map<String, String?>?,
            ) : Prepared {

                override fun plus(adaptation: Adaptation) =
                    if (adaptations.size == ADAPTATIONS_MAX) {
                        this
                    } else {
                        copy(adaptations = adaptations + adaptation)
                    }
            }

            data class Video(
                override val streamingSessionId: UUID,
                override val actualProductId: String,
                override val idealStartTimestampMs: IdealStartTimestampMs,
                val actualAssetPresentation: AssetPresentation,
                override val versionedCdm: VersionedCdm,
                override val actualQuality: VideoQuality,
                override val adaptations: List<Adaptation>,
                val actualStreamType: StreamType,
                override val mediaStorage: MediaStorage,
                override val extras: Map<String, String?>?,
            ) : Prepared {

                override fun plus(adaptation: Adaptation) =
                    if (adaptations.size == ADAPTATIONS_MAX) {
                        this
                    } else {
                        copy(adaptations = adaptations + adaptation)
                    }
            }

            data class UC(
                override val streamingSessionId: UUID,
                override val actualProductId: String,
                override val idealStartTimestampMs: IdealStartTimestampMs,
                override val versionedCdm: VersionedCdm,
                override val adaptations: List<Adaptation>,
                override val mediaStorage: MediaStorage,
                override val extras: Map<String, String?>?,
            ) : Prepared {

                override val actualQuality = AudioQuality.LOW

                override fun plus(adaptation: Adaptation) =
                    if (adaptations.size == ADAPTATIONS_MAX) {
                        this
                    } else {
                        copy(adaptations = adaptations + adaptation)
                    }
            }

            data class Broadcast(
                override val streamingSessionId: UUID,
                override val actualProductId: String,
                override val idealStartTimestampMs: IdealStartTimestampMs,
                override val versionedCdm: VersionedCdm,
                override val actualQuality: AudioQuality,
                override val adaptations: List<Adaptation>,
                override val mediaStorage: MediaStorage,
                override val extras: Map<String, String?>?,
            ) : Prepared {

                override fun plus(adaptation: Adaptation) =
                    if (adaptations.size == ADAPTATIONS_MAX) {
                        this
                    } else {
                        copy(adaptations = adaptations + adaptation)
                    }
            }
        }

        data class Started(
            val prepared: Prepared,
            val actualStartTimestampMs: Long,
            val stalls: List<Stall>,
        ) : Prepared by prepared {

            operator fun plus(stall: Stall) = if (stalls.size == STALLS_MAX) {
                this
            } else {
                copy(stalls = stalls + stall)
            }

            companion object {
                private const val STALLS_MAX = 100
            }
        }
    }

    companion object {

        private const val ADAPTATIONS_MAX = 100
    }
}
