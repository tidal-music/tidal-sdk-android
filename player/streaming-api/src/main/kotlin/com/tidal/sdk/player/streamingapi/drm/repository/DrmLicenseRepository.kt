package com.tidal.sdk.player.streamingapi.drm.repository

import com.tidal.sdk.player.streamingapi.drm.model.DrmLicense
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest

/** A repository meant to give you a drm license for a track or video. */
internal interface DrmLicenseRepository {

    /**
     * Returns a [DrmLicense] which we can use for decrypting a protected track or video.
     *
     * @param[drmLicenseRequest] The request we send to backend for getting a drm license back, as
     *   [DrmLicenseRequest]
     */
    suspend fun getDrmLicense(drmLicenseRequest: DrmLicenseRequest): DrmLicense
}
