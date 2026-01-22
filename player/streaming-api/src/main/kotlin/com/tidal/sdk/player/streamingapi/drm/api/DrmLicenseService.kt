package com.tidal.sdk.player.streamingapi.drm.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * A service interface for the drm endpoint.
 *
 * This will get a drm license from our backend.
 */
internal interface DrmLicenseService {

    /**
     * Returns DRM response body.
     *
     * @param[licenseUrl] The URL to call for the DRM license request.
     * @param[body] The binary payload from the CDM.
     */
    @POST
    suspend fun getWidevineLicense(
        @Url licenseUrl: String,
        @Body body: RequestBody,
    ): Response<ResponseBody>
}
