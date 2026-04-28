package com.tidal.sdk.player.streamingapi.playbackinfo.api

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * A service interface for the playback info endpoint.
 *
 * This will get broadcast playback info from our backend.
 */
internal interface PlaybackInfoService {

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
