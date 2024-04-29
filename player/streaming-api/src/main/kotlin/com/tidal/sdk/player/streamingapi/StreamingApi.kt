package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicense
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode

/**
 * The streaming api, where the main focus is to give you playback info or drm license for a track
 * or video.
 */
interface StreamingApi {

    /**
     * Returns a [PlaybackInfo] which we can use for playback of a track.
     *
     * @param[trackId] The requested track id as [Int].
     * @param[audioQuality] The requested audio quality as [AudioQuality].
     * @param[playbackMode] The requested playback mode as [PlaybackMode].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client,
     * for this streaming session.
     * @param[playlistUuid] The playlistUuid this play originates from as [String]. May be null.
     */
    suspend fun getTrackPlaybackInfo(
        trackId: Int,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        streamingSessionId: String,
        playlistUuid: String? = null,
    ): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for playback of a video.
     *
     * @param[videoId] The requested video id as [Int].
     * @param[videoQuality] The requested video quality as [VideoQuality].
     * @param[playbackMode] The requested playback mode as [PlaybackMode].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client,
     * for this streaming session.
     * @param[playlistUuid] The playlistUuid this play originates from as [String]. May be null.
     */
    suspend fun getVideoPlaybackInfo(
        videoId: Int,
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
     * Returns a [PlaybackInfo] which we can use for offline playback of a track.
     *
     * @param[trackId] The requested track id as [Int].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client,
     * for this streaming session.
     */
    suspend fun getOfflineTrackPlaybackInfo(
        trackId: Int,
        streamingSessionId: String,
    ): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for offline playback of a video.
     *
     * @param[videoId] The requested video id as [Int].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client,
     * for this streaming session.
     */
    suspend fun getOfflineVideoPlaybackInfo(
        videoId: Int,
        streamingSessionId: String,
    ): PlaybackInfo

    /**
     * Returns a [DrmLicense] which we can use for decrypting a protected track or video.
     *
     * @param[drmLicenseRequest] The request we send to backend for getting a drm license back, as
     * [DrmLicenseRequest]
     */
    suspend fun getDrmLicense(drmLicenseRequest: DrmLicenseRequest): DrmLicense
}
