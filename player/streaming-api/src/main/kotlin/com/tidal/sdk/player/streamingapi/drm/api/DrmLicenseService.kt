package com.tidal.sdk.player.streamingapi.drm.api

import com.tidal.sdk.player.streamingapi.drm.model.DrmLicense
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * A service interface for the drm endpoint.
 *
 * This will get a drm license from our backend.
 */
internal interface DrmLicenseService {

    /**
     * Returns a [DrmLicense] which we can use for decrypting a protected track or video.
     *
     * @param[drmLicenseRequest] The request body as [DrmLicenseRequest].
     */
    @POST("drm/licenses/widevine")
    suspend fun getWidevineLicense(@Body drmLicenseRequest: DrmLicenseRequest): DrmLicense
}
