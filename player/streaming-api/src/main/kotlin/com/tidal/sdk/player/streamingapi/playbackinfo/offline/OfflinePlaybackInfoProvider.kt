package com.tidal.sdk.player.streamingapi.playbackinfo.offline

import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/** An interface for getting track or video playback info from local storage. */
interface OfflinePlaybackInfoProvider {

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
