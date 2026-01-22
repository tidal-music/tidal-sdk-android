package com.tidal.sdk.player.streamingapi.playlogtest

import android.util.Base64
import com.google.gson.Gson
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.StreamingApi
import com.tidal.sdk.player.streamingapi.StreamingApiTimeoutConfig
import com.tidal.sdk.player.streamingapi.di.StreamingApiComponent
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import com.tidal.sdk.player.streamingapi.playbackinfo.offline.OfflinePlaybackInfoProvider
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * A test factory that creates a [StreamingApiComponent] with a pre-configured [StreamingApi] that
 * returns playback info without making any HTTP calls.
 */
class PlayLogTestStreamingApiComponentFactory(private val trackPlaybackInfo: PlaybackInfo.Track) :
    StreamingApiComponent.Factory {

    override fun create(
        okHttpClient: OkHttpClient,
        streamingApiTimeoutConfig: StreamingApiTimeoutConfig,
        gson: Gson,
        apiErrorFactory: ApiError.Factory,
        offlinePlaybackInfoProvider: OfflinePlaybackInfoProvider?,
        credentialsProvider: CredentialsProvider,
    ): StreamingApiComponent {
        return object : StreamingApiComponent {
            override val streamingApi: StreamingApi = PlayLogTestStreamingApi(trackPlaybackInfo)
        }
    }

    companion object {
        /**
         * Creates a [PlaybackInfo.Track] for testing with a BTS manifest pointing to the given URL.
         */
        fun createTrackPlaybackInfo(
            trackId: Int,
            audioUrl: String,
            streamingSessionId: String = "",
        ): PlaybackInfo.Track {
            // Create a simple BTS manifest JSON and encode it in Base64
            val btsManifestJson = """{"codecs":"mp4a.40.5","urls":["$audioUrl"]}"""
            val encodedManifest =
                Base64.encodeToString(
                    btsManifestJson.toByteArray(Charsets.ISO_8859_1),
                    Base64.NO_WRAP,
                )

            return PlaybackInfo.Track(
                trackId = trackId,
                audioQuality = AudioQuality.LOW,
                assetPresentation = AssetPresentation.FULL,
                audioMode = AudioMode.STEREO,
                manifestHash = "test-manifest-hash",
                streamingSessionId = streamingSessionId,
                manifestMimeType = ManifestMimeType.BTS,
                manifest = encodedManifest,
                licenseUrl = null,
                albumReplayGain = -9.97f,
                albumPeakAmplitude = 0.999969f,
                trackReplayGain = -9.69f,
                trackPeakAmplitude = 0.999969f,
                offlineRevalidateAt = 0,
                offlineValidUntil = 0,
            )
        }
    }
}

/** A test implementation of [StreamingApi] that returns pre-configured playback info. */
private class PlayLogTestStreamingApi(private val trackPlaybackInfo: PlaybackInfo.Track) :
    StreamingApi {

    override suspend fun getTrackPlaybackInfo(
        trackId: String,
        audioQuality: AudioQuality,
        playbackMode: PlaybackMode,
        immersiveAudio: Boolean,
        streamingSessionId: String,
        enableAdaptive: Boolean,
    ): PlaybackInfo {
        return trackPlaybackInfo.copy(streamingSessionId = streamingSessionId)
    }

    override suspend fun getVideoPlaybackInfo(
        videoId: String,
        videoQuality: VideoQuality,
        playbackMode: PlaybackMode,
        streamingSessionId: String,
        playlistUuid: String?,
    ): PlaybackInfo {
        throw UnsupportedOperationException("Not implemented for playlog test")
    }

    override suspend fun getBroadcastPlaybackInfo(
        djSessionId: String,
        streamingSessionId: String,
        audioQuality: AudioQuality,
    ): PlaybackInfo {
        throw UnsupportedOperationException("Not implemented for playlog test")
    }

    override suspend fun getUCPlaybackInfo(
        itemId: String,
        streamingSessionId: String,
    ): PlaybackInfo {
        throw UnsupportedOperationException("Not implemented for playlog test")
    }

    override suspend fun getOfflineTrackPlaybackInfo(
        trackId: String,
        streamingSessionId: String,
    ): PlaybackInfo {
        throw UnsupportedOperationException("Not implemented for playlog test")
    }

    override suspend fun getOfflineVideoPlaybackInfo(
        videoId: String,
        streamingSessionId: String,
    ): PlaybackInfo {
        throw UnsupportedOperationException("Not implemented for playlog test")
    }

    override suspend fun getDrmLicense(
        licenseUrl: String,
        payload: ByteArray,
    ): Response<ResponseBody> {
        throw UnsupportedOperationException("Not implemented for playlog test")
    }
}
