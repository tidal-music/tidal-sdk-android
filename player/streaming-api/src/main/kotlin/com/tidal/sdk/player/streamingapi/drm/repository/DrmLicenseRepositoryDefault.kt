package com.tidal.sdk.player.streamingapi.drm.repository

import com.tidal.sdk.player.streamingapi.drm.api.DrmLicenseService
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import dagger.Lazy
import retrofit2.HttpException

/**
 * Default implementation of DrmLicenseRepository.
 *
 * @property[drmLicenseService] A [DrmLicenseService] to retrieve drm license from backend.
 * @property[apiErrorMapperLazy] A [ApiErrorMapper] to transform exceptions when applicable.
 */
internal class DrmLicenseRepositoryDefault(
    private val drmLicenseService: DrmLicenseService,
    private val apiErrorMapperLazy: Lazy<ApiErrorMapper>,
) : DrmLicenseRepository {

    override suspend fun getDrmLicense(drmLicenseRequest: DrmLicenseRequest) =
        try {
            drmLicenseService.getWidevineLicense(drmLicenseRequest)
        } catch (e: HttpException) {
            throw apiErrorMapperLazy.get().map(e)
        }
}
