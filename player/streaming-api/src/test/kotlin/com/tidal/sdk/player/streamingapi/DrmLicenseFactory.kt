package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.streamingapi.drm.model.DrmLicense

object DrmLicenseFactory {

    fun default() = DrmLicense(
        ApiConstants.STREAMING_SESSION_ID,
        ApiConstants.DRM_PAYLOAD_RESPONSE,
    )

    fun emptyStreamingSessionId() = DrmLicense(
        "",
        ApiConstants.DRM_PAYLOAD_RESPONSE,
    )

    fun emptyPayload() = DrmLicense(
        ApiConstants.STREAMING_SESSION_ID,
        "",
    )
}
