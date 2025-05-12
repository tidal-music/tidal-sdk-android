package com.tidal.sdk.player.playbackengine.drm

import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

internal class DrmLicenseRequestFactory(private val playbackInfo: PlaybackInfo) {

    fun create(encodedRequestData: String) =
        DrmLicenseRequest(
            playbackInfo.streamingSessionId,
            playbackInfo.licenseSecurityToken ?: "",
            encodedRequestData,
        )
}
