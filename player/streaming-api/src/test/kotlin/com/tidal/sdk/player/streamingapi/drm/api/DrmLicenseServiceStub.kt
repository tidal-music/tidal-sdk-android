package com.tidal.sdk.player.streamingapi.drm.api

import com.tidal.sdk.player.streamingapi.DrmLicenseFactory
import okhttp3.RequestBody

/**
 * Stub implementation of [DrmLicenseService] that is useful for testing without any dependencies.
 */
@Suppress("ThrowingExceptionsWithoutMessageOrCause")
internal class DrmLicenseServiceStub : DrmLicenseService {

    override suspend fun getWidevineLicense(licenseUrl: String, body: RequestBody) =
        DrmLicenseFactory.default()
}
