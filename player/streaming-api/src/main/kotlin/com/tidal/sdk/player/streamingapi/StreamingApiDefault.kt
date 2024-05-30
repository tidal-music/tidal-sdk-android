package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import com.tidal.sdk.player.streamingapi.drm.repository.DrmLicenseRepository
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import com.tidal.sdk.player.streamingapi.playbackinfo.repository.PlaybackInfoRepository

/**
 * Default implementation of the streaming api.
 * This uses the repositories provided and fetches playback info or drm license.
 *
 * @param[playbackInfoRepository] A [PlaybackInfoRepository] that gets a playback info.
 * @param[drmLicenseRepository] An [DrmLicenseRepository] that gets a drm license.
 */
internal class StreamingApiDefault(
    private val playbackInfoRepository: PlaybackInfoRepository,
    private val drmLicenseRepository: DrmLicenseRepository,
) : StreamingApi {

    override suspend fun getTrackPlaybackInfo(
        trackId: Int,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        immersiveAudio: Boolean,
        streamingSessionId: String,
        playlistUuid: String?,
    ) = playbackInfoRepository.getTrackPlaybackInfo(
        trackId,
        audioQuality,
        playbackMode,
        immersiveAudio,
        streamingSessionId,
        playlistUuid,
    )

    override suspend fun getVideoPlaybackInfo(
        videoId: Int,
        videoQuality: VideoQuality,
        playbackMode: PlaybackMode,
        streamingSessionId: String,
        playlistUuid: String?,
    ) = playbackInfoRepository.getVideoPlaybackInfo(
        videoId,
        videoQuality,
        playbackMode,
        streamingSessionId,
        playlistUuid,
    )

    override suspend fun getBroadcastPlaybackInfo(
        djSessionId: String,
        streamingSessionId: String,
        audioQuality: AudioQuality,
    ) = playbackInfoRepository.getBroadcastPlaybackInfo(
        djSessionId,
        streamingSessionId,
        audioQuality,
    )

    override suspend fun getUCPlaybackInfo(
        itemId: String,
        streamingSessionId: String,
    ) = playbackInfoRepository.getUC(itemId, streamingSessionId)

    override suspend fun getOfflineTrackPlaybackInfo(
        trackId: Int,
        streamingSessionId: String,
    ) = playbackInfoRepository.getOfflineTrackPlaybackInfo(trackId, streamingSessionId)

    override suspend fun getOfflineVideoPlaybackInfo(
        videoId: Int,
        streamingSessionId: String,
    ) = playbackInfoRepository.getOfflineVideoPlaybackInfo(videoId, streamingSessionId)

    override suspend fun getDrmLicense(drmLicenseRequest: DrmLicenseRequest) =
        drmLicenseRepository.getDrmLicense(drmLicenseRequest)
}
