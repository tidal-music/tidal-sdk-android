package com.tidal.sdk.player.streamingapi.drm.repository

import com.tidal.sdk.player.streamingapi.drm.api.DrmLicenseService
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import dagger.Lazy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
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

    private val octetStream = "application/octet-stream".toMediaType()

    override suspend fun getDrmLicense(licenseUrl: String, payload: ByteArray) =
        try {
            val requestBody = payload.toRequestBody(octetStream)
            drmLicenseService.getWidevineLicense(licenseUrl, requestBody)
        } catch (e: HttpException) {
            throw apiErrorMapperLazy.get().map(e)
        }
}
