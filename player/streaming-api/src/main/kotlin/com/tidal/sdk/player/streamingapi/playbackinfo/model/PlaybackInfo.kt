package com.tidal.sdk.player.streamingapi.playbackinfo.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.offline.Storage

/**
 * Playback info of a given track or video. This describes all the details of the track or video,
 * including information of how to play it and some meta data around it.
 *
 * @property[streamingSessionId] A uuid generated by the client for this streaming session.
 * @property[manifestMimeType] A mime type of the manifest.
 * @property[manifest] Base64 encoded manifest.
 * @property[licenseSecurityToken] Token used to fetch DRM license if needed.
 * @property[offlineRevalidateAt] Indicates the earliest time at which the client is allowed to
 *   revalidate an offlined media product.
 * @property[offlineValidUntil] Indicates how long an offline is valid for.
 */
sealed interface PlaybackInfo {
    val streamingSessionId: String
    val manifestMimeType: ManifestMimeType
    val manifest: String
    val licenseSecurityToken: String?
    val albumReplayGain: Float
    val albumPeakAmplitude: Float
    val trackReplayGain: Float
    val trackPeakAmplitude: Float
    val offlineRevalidateAt: Long
    val offlineValidUntil: Long

    /**
     * Playback info with track specific properties.
     *
     * @property[trackId] The actual track id.
     * @property[audioQuality] The actual audio quality.
     * @property[assetPresentation] The actual asset presentation.
     * @property[audioMode] The actual audio mode.
     * @property[bitDepth] The actual bit depth.
     * @property[audioMode] The actual sample rate.
     * @property[manifestHash] Unique id for the manifest and the content it refers to.
     */
    @Keep
    data class Track(
        val trackId: Int,
        val audioQuality: AudioQuality,
        val assetPresentation: AssetPresentation,
        val audioMode: AudioMode,
        val bitDepth: Int?,
        val sampleRate: Int?,
        val manifestHash: String,
        override val streamingSessionId: String,
        override val manifestMimeType: ManifestMimeType,
        override val manifest: String,
        override val licenseSecurityToken: String? = null,
        override val albumReplayGain: Float,
        override val albumPeakAmplitude: Float,
        override val trackReplayGain: Float,
        override val trackPeakAmplitude: Float,
        override val offlineRevalidateAt: Long,
        override val offlineValidUntil: Long,
    ) : PlaybackInfo

    /**
     * Playback info with video specific properties.
     *
     * @property[videoId] The actual video id.
     * @property[videoQuality] The actual video quality.
     * @property[assetPresentation] The actual asset presentation.
     * @property[streamType] The actual stream type.
     * @property[manifestHash] Unique id for the manifest and the content it refers to.
     */
    @Keep
    data class Video(
        val videoId: Int,
        val videoQuality: VideoQuality,
        val assetPresentation: AssetPresentation,
        val streamType: StreamType,
        val manifestHash: String,
        override val streamingSessionId: String,
        override val manifestMimeType: ManifestMimeType,
        override val manifest: String,
        override val licenseSecurityToken: String? = null,
        override val albumReplayGain: Float,
        override val albumPeakAmplitude: Float,
        override val trackReplayGain: Float,
        override val trackPeakAmplitude: Float,
        override val offlineRevalidateAt: Long,
        override val offlineValidUntil: Long,
    ) : PlaybackInfo

    /**
     * Playback info with broadcast specific properties.
     *
     * @property[id] The actual broadcastId or djSessionId from a dj session.
     * @property[audioQuality] The actual audio quality.
     */
    @Keep
    data class Broadcast(
        val id: String,
        val audioQuality: AudioQuality,
        override val streamingSessionId: String,
        @SerializedName("manifestType") override val manifestMimeType: ManifestMimeType,
        override val manifest: String,
        override val licenseSecurityToken: String? = null,
        override val albumReplayGain: Float,
        override val albumPeakAmplitude: Float,
        override val trackReplayGain: Float,
        override val trackPeakAmplitude: Float,
        override val offlineRevalidateAt: Long,
        override val offlineValidUntil: Long,
    ) : PlaybackInfo

    /**
     * Playback info with UC specific properties.
     *
     * @property[id] The id of UC.
     * @property[url] The url of UC.
     */
    @Keep
    data class UC(
        val id: String,
        val url: String,
        override val streamingSessionId: String,
        override val manifestMimeType: ManifestMimeType,
        override val manifest: String,
        override val licenseSecurityToken: String?,
        override val albumReplayGain: Float,
        override val albumPeakAmplitude: Float,
        override val trackReplayGain: Float,
        override val trackPeakAmplitude: Float,
        override val offlineRevalidateAt: Long,
        override val offlineValidUntil: Long,
    ) : PlaybackInfo

    /**
     * Playback info with some extra information for offline content. The extra information is
     * needed to determine how the offline content was stored and how it can be retrieved, but also
     * to actually being able to play that content. We currently have three different ways to store
     * offline content, not all information is necessary at all times, therefore they come with
     * default values that needs to be filled differently in each different scenario.
     *
     * @property[offlineLicense] The stored offlineLicense in case it is protected.
     * @property[storage] Information about storage and path.
     * @property[partiallyEncrypted] Information about legacy encryption strategy.
     */
    interface Offline {
        val offlineLicense: String?
        val storage: Storage?
        val partiallyEncrypted: Boolean

        /**
         * Playback info with track specific properties for offline.
         *
         * @property[track] The original [PlaybackInfo.Track].
         */
        data class Track(
            val track: PlaybackInfo.Track,
            override val offlineLicense: String? = null,
            override val storage: Storage? = null,
            override val partiallyEncrypted: Boolean = false,
        ) : PlaybackInfo by track, Offline

        /**
         * Playback info with video specific properties for offline.
         *
         * @property[video] The original [PlaybackInfo.Video].
         */
        data class Video(
            val video: PlaybackInfo.Video,
            override val offlineLicense: String? = null,
            override val storage: Storage? = null,
            override val partiallyEncrypted: Boolean = false,
        ) : PlaybackInfo by video, Offline
    }
}
