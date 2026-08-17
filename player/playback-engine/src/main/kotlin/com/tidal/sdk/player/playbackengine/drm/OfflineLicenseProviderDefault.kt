package com.tidal.sdk.player.playbackengine.drm

import androidx.annotation.OptIn
import androidx.media3.common.Format
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.playbackengine.offline.OfflineLicenseProvider
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@OptIn(UnstableApi::class)
internal class OfflineLicenseProviderDefault(
    private val drmSessionManagerBuilder: DefaultDrmSessionManager.Builder,
    private val drmCallbackFactory: TidalMediaDrmCallbackFactory,
    private val offlineLicenseHelperFactory: OfflineLicenseHelperFactory,
    private val base64Codec: Base64Codec,
    private val coroutineDispatcher: CoroutineDispatcher,
) : OfflineLicenseProvider {

    override suspend fun acquireOfflineLicense(
        playbackInfo: PlaybackInfo,
        drmFormat: Format,
        extras: Extras?,
    ): String? =
        withContext(coroutineDispatcher) {
            if (drmFormat.drmInitData == null) {
                return@withContext null
            }

            val helper =
                createHelper(
                    playbackInfo = playbackInfo,
                    mode = DrmMode.OfflineAcquisition,
                    extras = extras,
                )
            try {
                helper.downloadLicense(drmFormat).requireLicense().encode()
            } finally {
                helper.release()
            }
        }

    override suspend fun renewOfflineLicense(
        playbackInfo: PlaybackInfo,
        offlineLicense: String,
        extras: Extras?,
    ): String =
        withContext(coroutineDispatcher) {
            val helper =
                createHelper(
                    playbackInfo = playbackInfo,
                    mode = DrmMode.OfflineRenewal,
                    extras = extras,
                )
            try {
                helper.renewLicense(offlineLicense.decode()).requireLicense().encode()
            } finally {
                helper.release()
            }
        }

    override suspend fun releaseOfflineLicense(offlineLicense: String) {
        withContext(coroutineDispatcher) {
            val callback = drmCallbackFactory.create("", "", DrmMode.OfflineRelease, null)
            val helper =
                offlineLicenseHelperFactory.create(drmSessionManagerBuilder.build(callback))
            try {
                helper.releaseLicense(offlineLicense.decode())
            } finally {
                helper.release()
            }
        }
    }

    private fun createHelper(playbackInfo: PlaybackInfo, mode: DrmMode, extras: Extras?) =
        offlineLicenseHelperFactory.create(
            drmSessionManagerBuilder.build(
                drmCallbackFactory.create(
                    playbackInfo.requireLicenseUrl(),
                    playbackInfo.streamingSessionId,
                    mode,
                    extras,
                )
            )
        )

    private fun PlaybackInfo.requireLicenseUrl(): String {
        require(this is PlaybackInfo.Track || this is PlaybackInfo.Video) {
            "Offline licenses are only supported for tracks and videos."
        }
        return requireNotNull(licenseUrl?.takeIf(String::isNotBlank)) {
            "A license URL is required for protected offline content."
        }
    }

    private fun ByteArray?.requireLicense(): ByteArray {
        check(!isNullOrEmpty()) { "Widevine did not return a persistent key-set ID." }
        return requireNotNull(this)
    }

    private fun ByteArray.encode() = String(base64Codec.encode(this), Charsets.UTF_8)

    private fun String.decode(): ByteArray {
        require(isNotBlank()) { "The offline license must not be blank." }
        return base64Codec.decode(toByteArray(Charsets.UTF_8))
    }
}
