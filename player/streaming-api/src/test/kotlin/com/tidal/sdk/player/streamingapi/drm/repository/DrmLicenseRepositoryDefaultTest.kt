package com.tidal.sdk.player.streamingapi.drm.repository

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.player.streamingapi.DrmLicenseFactory
import com.tidal.sdk.player.streamingapi.drm.api.DrmLicenseServiceStub
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicense
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

/** Test that the [DrmLicenseRepository] returns correct [DrmLicense] or throws exception. */
internal class DrmLicenseRepositoryDefaultTest {

    private val drmLicenseService = DrmLicenseServiceStub()

    private val apiErrorMapperLazy = { mock<ApiErrorMapper>() }

    private val drmLicenseRepository =
        DrmLicenseRepositoryDefault(drmLicenseService, apiErrorMapperLazy)

    @Test
    fun getDrmLicenseShouldThrowWhenUncaughtExceptionIsThrown() {
        assertFailure {
                runBlocking {
                    getDrmLicense(DrmLicenseServiceStub.DRM_LICENSE_SSID_FOR_UNCAUGHT_EXCEPTION)
                }
            }
            .hasClass(NullPointerException::class)
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectWhenDrmLicenseIsReturned() = runBlocking {
        val drmLicense = getDrmLicense(DrmLicenseServiceStub.DRM_LICENSE_SSID_SUCCESS)

        assertThat(drmLicense).isEqualTo(DrmLicenseFactory.default())
    }

    private suspend fun getDrmLicense(streamingSessionId: String) =
        drmLicenseRepository.getDrmLicense(
            DrmLicenseRequest(
                streamingSessionId,
                ApiConstants.LICENSE_SECURITY_TOKEN,
                ApiConstants.DRM_PAYLOAD_REQUEST,
            )
        )
}
