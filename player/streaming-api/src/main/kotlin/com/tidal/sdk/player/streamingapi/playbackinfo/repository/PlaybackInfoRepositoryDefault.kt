package com.tidal.sdk.player.streamingapi.playbackinfo.repository

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.playbackinfo.api.PlaybackInfoService
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import com.tidal.sdk.player.streamingapi.playbackinfo.offline.OfflinePlaybackInfoProvider
import dagger.Lazy
import retrofit2.HttpException

/**
 * Default implementation of PlaybackInfoRepository.
 *
 * @property[offlinePlaybackInfoProvider] An optional [OfflinePlaybackInfoProvider] to retrieve
 * playback info from local storage, intended for offline playback.
 * @property[playbackInfoService] A [PlaybackInfoService] to retrieve playback info from backend.
 * @property[apiErrorMapperLazy] A [ApiErrorMapper] to transform exceptions
 * when applicable.
 */
internal class PlaybackInfoRepositoryDefault(
    private val offlinePlaybackInfoProvider: OfflinePlaybackInfoProvider?,
    private val playbackInfoService: PlaybackInfoService,
    private val apiErrorMapperLazy: Lazy<ApiErrorMapper>,
) : PlaybackInfoRepository {

    override suspend fun getTrackPlaybackInfo(
        trackId: String,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        immersiveAudio: Boolean,
        streamingSessionId: String,
        playlistUuid: String?,
    ) = try {
        playbackInfoService.getTrackPlaybackInfo(
            trackId,
            playbackMode,
            AssetPresentation.FULL,
            audioQuality,
            immersiveAudio,
            streamingSessionId,
            playlistUuid,
        )
    } catch (e: HttpException) {
        throw apiErrorMapperLazy.get().map(e)
    }

    override suspend fun getVideoPlaybackInfo(
        videoId: String,
        videoQuality: VideoQuality,
        playbackMode: PlaybackMode,
        streamingSessionId: String,
        playlistUuid: String?,
    ) = try {
        playbackInfoService.getVideoPlaybackInfo(
            videoId,
            playbackMode,
            AssetPresentation.FULL,
            videoQuality,
            streamingSessionId,
            playlistUuid,
        )
    } catch (e: HttpException) {
        throw apiErrorMapperLazy.get().map(e)
    }

    override suspend fun getBroadcastPlaybackInfo(
        djSessionId: String,
        streamingSessionId: String,
        audioQuality: AudioQuality,
    ) = try {
        playbackInfoService.getBroadcastPlaybackInfo(djSessionId, audioQuality)
            .copy(streamingSessionId = streamingSessionId)
    } catch (e: HttpException) {
        throw apiErrorMapperLazy.get().map(e)
    }

    override suspend fun getUC(
        itemId: String,
        streamingSessionId: String,
    ) = PlaybackInfo.UC(
        itemId,
        "https://fsu.fa.tidal.com/storage/$itemId.m3u8",
        streamingSessionId,
        ManifestMimeType.EMU,
        "",
        null,
        0f,
        0f,
        0f,
        0f,
        0,
        0,
    )

    override suspend fun getOfflineTrackPlaybackInfo(
        trackId: String,
        streamingSessionId: String,
    ) = offlinePlaybackInfoProvider?.getOfflineTrackPlaybackInfo(trackId, streamingSessionId)
        ?: throw NullPointerException("No OfflinePlaybackInfoProvider provided")

    override suspend fun getOfflineVideoPlaybackInfo(
        videoId: String,
        streamingSessionId: String,
    ) = offlinePlaybackInfoProvider?.getOfflineVideoPlaybackInfo(videoId, streamingSessionId)
        ?: throw NullPointerException("No OfflinePlaybackInfoProvider provided")
}
