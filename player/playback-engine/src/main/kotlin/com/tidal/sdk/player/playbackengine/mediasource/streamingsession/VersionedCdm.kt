package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import android.media.MediaDrm
import androidx.annotation.Keep
import androidx.media3.common.C
import androidx.media3.exoplayer.drm.ExoMediaDrm
import com.tidal.sdk.player.events.model.PlaybackStatistics
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

@Keep
internal class VersionedCdm private constructor(
    val cdm: PlaybackStatistics.Payload.Cdm,
    val version: String?,
) {

    @Suppress("UnsafeOptInUsageError")
    class Calculator(exoMediaDrmProvider: ExoMediaDrm.Provider) {

        private val widevine by lazy(LazyThreadSafetyMode.NONE) {
            VersionedCdm(
                PlaybackStatistics.Payload.Cdm.WIDEVINE,
                run {
                    val exoMediaDrm = exoMediaDrmProvider.acquireExoMediaDrm(C.WIDEVINE_UUID)
                    val version = exoMediaDrm.getPropertyString(MediaDrm.PROPERTY_VERSION)
                    exoMediaDrm.release()
                    version
                },
            )
        }

        operator fun invoke(playbackInfo: PlaybackInfo) =
            if (playbackInfo.licenseSecurityToken.isNullOrEmpty()) {
                VersionedCdm(PlaybackStatistics.Payload.Cdm.NONE, null)
            } else {
                widevine
            }
    }
}
