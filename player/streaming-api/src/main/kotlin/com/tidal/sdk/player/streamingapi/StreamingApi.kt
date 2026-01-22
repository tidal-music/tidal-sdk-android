package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * The streaming api, where the main focus is to give you playback info or drm license for a track
 * or video.
 */
interface StreamingApi {

    /**
     * Returns a [PlaybackInfo] which we can use for playback of a track.
     *
     * @param[trackId] The requested track id as [String].
     * @param[audioQuality] The requested audio quality as [AudioQuality].
     * @param[playbackMode] The requested playback mode as [PlaybackMode].
     * @param[immersiveAudio] The requested option to include immersive audio or not.
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client, for
     *   this streaming session.
     * @param[enableAdaptive] Whether to enable adaptive streaming.
     */
    @Suppress("LongParameterList")
    suspend fun getTrackPlaybackInfo(
        trackId: String,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        immersiveAudio: Boolean,
        streamingSessionId: String,
        enableAdaptive: Boolean,
    ): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for playback of a video.
     *
     * @param[videoId] The requested video id as [String].
     * @param[videoQuality] The requested video quality as [VideoQuality].
     * @param[playbackMode] The requested playback mode as [PlaybackMode].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client, for
     *   this streaming session.
     * @param[playlistUuid] The playlistUuid this play originates from as [String]. May be null.
     */
    suspend fun getVideoPlaybackInfo(
        videoId: String,
        videoQuality: VideoQuality,
        playbackMode: PlaybackMode,
        streamingSessionId: String,
        playlistUuid: String? = null,
    ): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for playback of a broadcast.
     *
     * @param[djSessionId] The requested dj session id as [String].
     * @param[streamingSessionId] The streaming session id as [String].
     * @param[audioQuality] The requested audio quality as [AudioQuality].
     */
    suspend fun getBroadcastPlaybackInfo(
        djSessionId: String,
        streamingSessionId: String,
        audioQuality: AudioQuality,
    ): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for playback of UC.
     *
     * @param[itemId] The requested item id as [String].
     * @param[streamingSessionId] The streaming session id as [String].
     */
    suspend fun getUCPlaybackInfo(itemId: String, streamingSessionId: String): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for offline playback of a track.
     *
     * @param[trackId] The requested track id as [String].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client, for
     *   this streaming session.
     */
    suspend fun getOfflineTrackPlaybackInfo(
        trackId: String,
        streamingSessionId: String,
    ): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for offline playback of a video.
     *
     * @param[videoId] The requested video id as [String].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client, for
     *   this streaming session.
     */
    suspend fun getOfflineVideoPlaybackInfo(
        videoId: String,
        streamingSessionId: String,
    ): PlaybackInfo

    /**
     * Returns the DRM binary response.
     *
     * @param[payload] The binary payload to send to backend for getting a drm license back.
     */
    suspend fun getDrmLicense(licenseUrl: String, payload: ByteArray): Response<ResponseBody>
}
