package com.tidal.sdk.player.streamingapi.drm.repository

import okhttp3.ResponseBody
import retrofit2.Response

/** A repository meant to give you a drm license for a track or video. */
internal interface DrmLicenseRepository {

    /**
     * Returns the DRM which we can use for decrypting a protected track or video.
     *
     * @param[payload] The binary payload to send to backend for getting a drm license back.
     */
    suspend fun getDrmLicense(licenseUrl: String, payload: ByteArray): Response<ResponseBody>
}
