package com.tidal.sdk.player.streamingapi.drm.api

import com.tidal.sdk.player.streamingapi.DrmLicenseFactory
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import org.junit.jupiter.api.fail

/**
 * Stub implementation of [DrmLicenseService] that is useful for testing without any
 * dependencies.
 *
 * This implementation hard codes the result of the function to a various values useful in tests.
 */
@Suppress("ThrowingExceptionsWithoutMessageOrCause")
internal class DrmLicenseServiceStub : DrmLicenseService {

    override suspend fun getWidevineLicense(drmLicenseRequest: DrmLicenseRequest) =
        when (drmLicenseRequest.streamingSessionId) {
            DRM_LICENSE_SSID_FOR_UNCAUGHT_EXCEPTION -> throw NullPointerException()
            DRM_LICENSE_SSID_SUCCESS -> DrmLicenseFactory.default()
            else -> fail("Unsupported call")
        }

    companion object {
        const val DRM_LICENSE_SSID_SUCCESS = "success"
        const val DRM_LICENSE_SSID_FOR_UNCAUGHT_EXCEPTION = "uncaught"
    }
}
