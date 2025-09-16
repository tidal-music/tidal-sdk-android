package com.tidal.sdk.player.streamingapi.playbackinfo.repository

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode

/** A repository meant to give you track or video playback info. */
internal interface PlaybackInfoRepository {

    /**
     * Returns a [PlaybackInfo] which we can use for playback of a track.
     *
     * @param[trackId] The requested track id as [String].
     * @param[audioQuality] The requested audio quality as [AudioQuality].
     * @param[playbackMode] The requested playback mode as [PlaybackMode].
     * @param[immersiveAudio] The requested option to include immersive audio or not.
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client, for
     *   this streaming session.
     * @param[playlistUuid] The playlistUuid this play originates from as [String]. May be null.
     */
    suspend fun getTrackPlaybackInfo(
        trackId: String,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        immersiveAudio: Boolean,
        streamingSessionId: String,
        playlistUuid: String?,
    ): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for playback of a track from Top.
     *
     * @param[trackId] The requested track id as [String].
     * @param[audioQuality] The requested audio quality as [AudioQuality].
     * @param[playbackMode] The requested playback mode as [PlaybackMode].
     * @param[immersiveAudio] The requested option to include immersive audio or not.
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client, for
     *   this streaming session.
     * @param[playlistUuid] The playlistUuid this play originates from as [String]. May be null.
     */
    suspend fun getTrackPlaybackInfoTop(
        trackId: String,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        immersiveAudio: Boolean,
        streamingSessionId: String,
        playlistUuid: String?,
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
        playlistUuid: String?,
    ): PlaybackInfo

    /**
     * Returns a [PlaybackInfo] which we can use for playback of a broadcast.
     *
     * @param[djSessionId] The requested dj session id as [String].
     * @param[streamingSessionId] The requested streaming session id as [String].
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
     * @param[streamingSessionId] The requested streaming session id as [String].
     */
    suspend fun getUC(itemId: String, streamingSessionId: String): PlaybackInfo

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
}
