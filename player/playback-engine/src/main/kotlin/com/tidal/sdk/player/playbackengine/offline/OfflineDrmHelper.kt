package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManager
import com.tidal.sdk.player.commonandroid.Base64Codec

/** This class is used to help set offline licenses for use with drm protected offline content. */
internal class OfflineDrmHelper(private val base64Codec: Base64Codec) {

    fun setOfflineLicense(offlineLicense: String, drmSessionManager: DrmSessionManager) {
        val decodedOfflineLicense = base64Codec.decode(offlineLicense.toByteArray(CHARSET))
        (drmSessionManager as? DefaultDrmSessionManager)?.setMode(
            DefaultDrmSessionManager.MODE_PLAYBACK,
            decodedOfflineLicense,
        )
    }

    companion object {
        private val CHARSET = Charsets.UTF_8
    }
}
