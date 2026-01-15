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
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests.UriSchemeTrackManifestsIdGet
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests.UsageTrackManifestsIdGet
import com.tidal.sdk.tidalapi.generated.models.TrackManifestsAttributes.Formats
import com.tidal.sdk.tidalapi.generated.models.TrackManifestsAttributes.TrackPresentation
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
            val data =
                trackManifests
                    .trackManifestsIdGet(
                        id = trackId,
                        manifestType = TrackManifests.ManifestTypeTrackManifestsIdGet.MPEG_DASH,
                        formats = getRequestedFormats(audioQuality, immersiveAudio),
                        uriScheme = UriSchemeTrackManifestsIdGet.DATA,
                        usage =
                            if (playbackMode == PlaybackMode.STREAM)
                                UsageTrackManifestsIdGet.PLAYBACK
                            else UsageTrackManifestsIdGet.DOWNLOAD,
                        adaptive = false,
                    )
                    .body()
                    ?.data
            PlaybackInfo.Track(
                trackId = data?.id?.toInt() ?: -1,
                audioQuality = getAudioQualityFromFormats(data?.attributes?.formats, audioQuality),
                assetPresentation = convertTrackPresentation(data?.attributes?.trackPresentation),
                audioMode = getAudioModeFromFormats(data?.attributes?.formats),
                bitDepth = null, // Not available in new API
                sampleRate = null, // Not available in new API
                manifestHash = data?.attributes?.hash.orEmpty(),
                streamingSessionId = streamingSessionId,
                manifestMimeType = ManifestMimeType.DASH,
                manifest = extractManifest(data?.attributes?.uri.orEmpty()),
                licenseUrl = data?.attributes?.drmData?.licenseUrl,
                albumReplayGain = data?.attributes?.albumAudioNormalizationData?.replayGain ?: -1f,
                albumPeakAmplitude =
                    data?.attributes?.albumAudioNormalizationData?.peakAmplitude ?: -1f,
                trackReplayGain = data?.attributes?.trackAudioNormalizationData?.replayGain ?: -1f,
                trackPeakAmplitude =
                    data?.attributes?.trackAudioNormalizationData?.peakAmplitude ?: -1f,
                offlineRevalidateAt = -1,
                offlineValidUntil = -1,
            )
        } catch (e: HttpException) {
            throw apiErrorMapperLazy.get().map(e)
        }

    /**
     * Converts AudioQuality to a list of format strings for track manifest requests.
     *
     * Always includes HEAACV1 as the base format, then adds higher quality formats based on the
     * AudioQuality level.
     *
     * @param audioQuality The audio quality level to convert
     * @param immersiveAudio Whether to include immersive audio formats
     * @return List of format strings compatible with track manifest API
     */
    private fun getRequestedFormats(
        audioQuality: AudioQuality,
        immersiveAudio: Boolean,
    ): List<String> {
        val formats = mutableListOf(Formats.HEAACV1.value)

        if (audioQuality >= AudioQuality.HIGH) formats += Formats.AACLC.value
        if (audioQuality >= AudioQuality.LOSSLESS) formats += Formats.FLAC.value
        if (audioQuality == AudioQuality.HI_RES_LOSSLESS) formats += Formats.FLAC_HIRES.value

        if (immersiveAudio) formats += Formats.EAC3_JOC.value

        return formats
    }

    /**
     * Determines the highest AudioQuality from a list of available formats.
     *
     * @param formats List of available formats from TrackManifestsAttributes
     * @param fallback AudioQuality to return if formats is null/empty or no recognized formats
     *   found
     * @return The highest AudioQuality available in the formats, or fallback if none found
     */
    private fun getAudioQualityFromFormats(
        formats: List<Formats>?,
        fallback: AudioQuality,
    ): AudioQuality {
        if (formats.isNullOrEmpty()) {
            return fallback
        }

        if (formats.contains(Formats.FLAC_HIRES)) return AudioQuality.HI_RES_LOSSLESS
        if (formats.contains(Formats.FLAC)) return AudioQuality.LOSSLESS
        if (formats.contains(Formats.AACLC)) return AudioQuality.HIGH
        if (formats.contains(Formats.HEAACV1)) return AudioQuality.LOW

        return fallback
    }

    /**
     * Determines the AudioMode from a list of available formats.
     *
     * @param formats List of available formats from TrackManifestsAttributes
     * @return DOLBY_ATMOS if EAC3_JOC format is present, otherwise STEREO
     */
    private fun getAudioModeFromFormats(formats: List<Formats>?): AudioMode {
        if (formats != null && formats.contains(Formats.EAC3_JOC)) {
            return AudioMode.DOLBY_ATMOS
        }

        return AudioMode.STEREO
    }

    /**
     * Converts TrackPresentation to AssetPresentation.
     *
     * @param presentation The track presentation from TrackManifestsAttributes
     * @return PREVIEW if presentation is PREVIEW, otherwise FULL (including null case)
     */
    private fun convertTrackPresentation(presentation: TrackPresentation?): AssetPresentation {
        return when (presentation) {
            TrackPresentation.PREVIEW -> AssetPresentation.PREVIEW
            TrackPresentation.FULL -> AssetPresentation.FULL
            null -> AssetPresentation.FULL // Default fallback
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
}
