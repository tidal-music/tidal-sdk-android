package com.tidal.sdk.player.streamingapi.playbackinfo.api

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * A service interface for the playback info endpoint.
 *
 * This will get track or video playback info from our backend.
 */
internal interface PlaybackInfoService {

    /**
     * Returns a [PlaybackInfo.Track] which we can use for playback of a track.
     *
     * @param[trackId] The requested track id as [String].
     * @param[playbackMode] The requested playback mode as [PlaybackMode].
     * @param[assetPresentation] The requested asset presentation as [AssetPresentation].
     * @param[audioQuality] The requested audio quality as [AudioQuality].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client, for
     *   this streaming session.
     * @param[playlistUuid] The playlistUuid this play originates from as [String]. May be null.
     */
    @GET("tracks/{id}/playbackinfo")
    suspend fun getTrackPlaybackInfo(
        @Path("id") trackId: String,
        @Query("playbackmode") playbackMode: PlaybackMode,
        @Query("assetpresentation") assetPresentation: AssetPresentation,
        @Query("audioquality") audioQuality: AudioQuality,
        @Query("immersiveaudio") immersiveAudio: Boolean,
        @Header("x-tidal-streamingsessionid") streamingSessionId: String,
        @Header("x-tidal-playlistuuid") playlistUuid: String?,
    ): PlaybackInfo.Track

    /**
     * Returns a [PlaybackInfo.Video] which we can use for playback of a video.
     *
     * @param[videoId] The requested video id as [String].
     * @param[playbackMode] The requested playback mode as [PlaybackMode].
     * @param[assetPresentation] The requested asset presentation as [AssetPresentation].
     * @param[videoQuality] The requested video quality as [VideoQuality].
     * @param[streamingSessionId] The streaming session uuid as [String], created by the client, for
     *   this streaming session.
     * @param[playlistUuid] The playlistUuid this play originates from as [String]. May be null.
     */
    @GET("videos/{id}/playbackinfo")
    suspend fun getVideoPlaybackInfo(
        @Path("id") videoId: String,
        @Query("playbackmode") playbackMode: PlaybackMode,
        @Query("assetpresentation") assetPresentation: AssetPresentation,
        @Query("videoquality") videoQuality: VideoQuality,
        @Header("x-tidal-streamingsessionid") streamingSessionId: String,
        @Header("x-tidal-playlistuuid") playlistUuid: String?,
    ): PlaybackInfo.Video

    /**
     * Returns a [PlaybackInfo.Broadcast] which we can use for playback of a broadcast.
     *
     * @param[djSessionId] The requested dj session id as [String].
     * @param[audioQuality] The requested audio quality as [AudioQuality].
     */
    @GET("broadcasts/{djSessionId}/playbackinfo")
    suspend fun getBroadcastPlaybackInfo(
        @Path("djSessionId") djSessionId: String,
        @Query("audioquality") audioQuality: AudioQuality,
    ): PlaybackInfo.Broadcast
}
