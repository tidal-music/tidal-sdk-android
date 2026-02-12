package com.tidal.sdk.player.playbackengine.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.PreviewReason
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.playbackengine.AssetSource

/**
 * Contains playback related information for an active [MediaProduct].
 *
 * @property[productId] The product id of the media product being played. Might differ from
 *   requested in case of a replacement.
 * @property[assetPresentation] The asset presentation of the media product being played. Might
 *   differ from requested in case of a replacement.
 * @property[duration] The duration, in seconds, of the media product being played. Might differ
 *   from the meta data connected to the requested in case of a replacement.
 * @property[assetSource] The source of the assets that is used for playback
 * @property[playbackSessionId] The playback session id that is used for PlayLog and
 *   StreamingMetrics (streamingSessionId) for the currently active playback.
 * @property[referenceId] Loopback of the referenceId in the media product that this playback
 *   context relates to.
 */
sealed interface PlaybackContext {
    val productId: String
    val assetPresentation: AssetPresentation
    val duration: Float
    val assetSource: AssetSource
    val playbackSessionId: String
    val referenceId: String?

    /**
     * Playback context with track specific properties.
     *
     * @param[audioMode] The audio mode of the media product being played (or null). Might differ
     *   from requested in case of a replacement.
     * @param[audioQuality] The audio quality of the media product being played (or null). Might
     *   differ from requested in case of a replacement.
     * @param[audioBitRate] The bit rate indicated in number of bits per second of the media product
     *   being played.
     * @param[audioBitDepth] The bit depth indicated in number of bits used per sample of the media
     *   product being played.
     * @param[audioCodec] The codec name of the media product being played.
     * @param[audioSampleRate] The sample rate indicated in Hz of the media product being played.
     */
    data class Track(
        val audioMode: AudioMode?,
        val audioQuality: AudioQuality?,
        val audioBitRate: Int?,
        val audioBitDepth: Int?,
        val audioCodec: String?,
        val audioSampleRate: Int?,
        val previewReason: PreviewReason?,
        override val productId: String,
        override val assetPresentation: AssetPresentation,
        override val duration: Float,
        override val assetSource: AssetSource,
        override val playbackSessionId: String,
        override val referenceId: String?,
    ) : PlaybackContext

    /**
     * Playback info with track specific properties.
     *
     * @param[streamType] The stream type of the media product being played (or null). Might differ
     *   from requested in case of a replacement.
     * @param[videoQuality] The video quality of the media product being played (or null). Might
     *   differ from requested in case of a replacement.
     */
    data class Video(
        val streamType: StreamType?,
        val videoQuality: VideoQuality?,
        override val productId: String,
        override val assetPresentation: AssetPresentation,
        override val duration: Float,
        override val assetSource: AssetSource,
        override val playbackSessionId: String,
        override val referenceId: String?,
    ) : PlaybackContext
}
