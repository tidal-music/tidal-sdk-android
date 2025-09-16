package com.tidal.sdk.player.streamingapi.playbackinfo.repository

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.playbackinfo.api.PlaybackInfoService
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import com.tidal.sdk.player.streamingapi.playbackinfo.offline.OfflinePlaybackInfoProvider
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests
import dagger.Lazy
import retrofit2.HttpException

/**
 * Default implementation of PlaybackInfoRepository.
 *
 * @property[offlinePlaybackInfoProvider] An optional [OfflinePlaybackInfoProvider] to retrieve
 *   playback info from local storage, intended for offline playback.
 * @property[playbackInfoService] A [PlaybackInfoService] to retrieve playback info from backend.
 * @property[apiErrorMapperLazy] A [ApiErrorMapper] to transform exceptions when applicable.
 */
internal class PlaybackInfoRepositoryDefault(
    private val offlinePlaybackInfoProvider: OfflinePlaybackInfoProvider?,
    private val playbackInfoService: PlaybackInfoService,
    private val apiErrorMapperLazy: Lazy<ApiErrorMapper>,
    private val trackManifests: TrackManifests,
) : PlaybackInfoRepository {

    override suspend fun getTrackPlaybackInfo(
        trackId: String,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        immersiveAudio: Boolean,
        streamingSessionId: String,
        playlistUuid: String?,
    ) =
        try {
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

    override suspend fun getTrackPlaybackInfoTop(
        trackId: String,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        immersiveAudio: Boolean,
        streamingSessionId: String,
        playlistUuid: String?,
    ) =
        try {
            val data =
                trackManifests
                    .trackManifestsIdGet(
                        trackId,
                        MANIFEST_TYPE,
                        audioQuality.toFormats(),
                        DATA_URI_SCHEME,
                        if (playbackMode == PlaybackMode.STREAM) PLAYBACK_USAGE else DOWNLOAD_USAGE,
                        "false",
                    )
                    .body()
                    ?.data
            PlaybackInfo.Track(
                data?.id?.toInt() ?: -1,
                AudioQuality.HI_RES_LOSSLESS,
                if (data?.attributes?.trackPresentation?.name == TRACK_PREVIEW_PRESENTATION)
                    AssetPresentation.PREVIEW
                else AssetPresentation.FULL,
                AudioMode.STEREO,
                null,
                null,
                data?.attributes?.hash.orEmpty(),
                streamingSessionId,
                ManifestMimeType.DASH,
                extractManifest(data?.attributes?.uri.orEmpty()),
                data?.attributes?.drmData?.licenseUrl,
                data?.attributes?.albumAudioNormalizationData?.replayGain ?: -1f,
                data?.attributes?.albumAudioNormalizationData?.peakAmplitude ?: -1f,
                data?.attributes?.trackAudioNormalizationData?.replayGain ?: -1f,
                data?.attributes?.trackAudioNormalizationData?.peakAmplitude ?: -1f,
                -1,
                -1,
            )
        } catch (e: HttpException) {
            throw apiErrorMapperLazy.get().map(e)
        }

    private fun AudioQuality.toFormats(): String {
        return when (this) {
            AudioQuality.LOW -> "HEAACV1"
            AudioQuality.HIGH -> "HEAACV1,AACLC"
            AudioQuality.LOSSLESS -> "HEAACV1,AACLC,FLAC"
            AudioQuality.HI_RES_LOSSLESS -> "HEAACV1,AACLC,FLAC,FLAC_HIRES"
        }
    }

    private fun extractManifest(dataUrl: String): String {
        val commaIndex = dataUrl.indexOf(',')
        require(commaIndex >= 0) { "Invalid manifest" }
        return dataUrl.substring(commaIndex + 1)
    }

    override suspend fun getVideoPlaybackInfo(
        videoId: String,
        videoQuality: VideoQuality,
        playbackMode: PlaybackMode,
        streamingSessionId: String,
        playlistUuid: String?,
    ) =
        try {
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
    ) =
        try {
            playbackInfoService
                .getBroadcastPlaybackInfo(djSessionId, audioQuality)
                .copy(streamingSessionId = streamingSessionId)
        } catch (e: HttpException) {
            throw apiErrorMapperLazy.get().map(e)
        }

    override suspend fun getUC(itemId: String, streamingSessionId: String) =
        PlaybackInfo.UC(
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

    override suspend fun getOfflineTrackPlaybackInfo(trackId: String, streamingSessionId: String) =
        offlinePlaybackInfoProvider?.getOfflineTrackPlaybackInfo(trackId, streamingSessionId)
            ?: throw NullPointerException("No OfflinePlaybackInfoProvider provided")

    override suspend fun getOfflineVideoPlaybackInfo(videoId: String, streamingSessionId: String) =
        offlinePlaybackInfoProvider?.getOfflineVideoPlaybackInfo(videoId, streamingSessionId)
            ?: throw NullPointerException("No OfflinePlaybackInfoProvider provided")

    private companion object {
        const val MANIFEST_TYPE = "MPEG_DASH"
        const val DATA_URI_SCHEME = "DATA"
        const val PLAYBACK_USAGE = "PLAYBACK"
        const val DOWNLOAD_USAGE = "DOWNLOAD"
        const val TRACK_PREVIEW_PRESENTATION = "PREVIEW"
    }
}
