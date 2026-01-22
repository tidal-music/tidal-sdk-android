package com.tidal.sdk.player.streamingapi.drm.repository

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.player.streamingapi.DrmLicenseFactory
import com.tidal.sdk.player.streamingapi.drm.api.DrmLicenseService
import com.tidal.sdk.player.streamingapi.drm.api.DrmLicenseServiceStub
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

/** Test that the [DrmLicenseRepository] returns correct response or throws exception. */
internal class DrmLicenseRepositoryDefaultTest {

    private val drmLicenseService = DrmLicenseServiceStub()

    private val apiErrorMapperLazy = { mock<ApiErrorMapper>() }

    private val drmLicenseRepository =
        DrmLicenseRepositoryDefault(drmLicenseService, apiErrorMapperLazy)

    @Test
    fun getDrmLicenseShouldThrowWhenUncaughtExceptionIsThrown() = runBlocking {
        val mockService = mock<DrmLicenseService>()
        val mockApiErrorMapper = mock<ApiErrorMapper>()
        val mockLazy = mock<Lazy<ApiErrorMapper>> { on { get() } doReturn mockApiErrorMapper }

        val repository = DrmLicenseRepositoryDefault(mockService, mockLazy)

        val licenseUrl = ApiConstants.LICENSE_URL
        val payload = ApiConstants.DRM_PAYLOAD_REQUEST
        val httpException = HttpException(Response.error<String>(500, "error".toResponseBody()))
        val mappedException = RuntimeException("Mapped error")

        whenever(mockService.getWidevineLicense(any(), any())).thenThrow(httpException)
        whenever(mockApiErrorMapper.map(httpException)).thenReturn(mappedException)

        assertFailure { repository.getDrmLicense(licenseUrl, payload) }
            .hasClass(RuntimeException::class)
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectWhenDrmLicenseIsReturned() = runBlocking {
        val licenseUrl = ApiConstants.LICENSE_URL
        val payload = ApiConstants.DRM_PAYLOAD_REQUEST
        val expected = DrmLicenseFactory.default()

        val actual = getDrmLicense(licenseUrl, payload)

        assertThat(actual.isSuccessful).isEqualTo(expected.isSuccessful)

        val actualBytes = actual.body()?.bytes()
        val expectedBytes = expected.body()?.bytes()

        assertThat(actualBytes).isNotNull()
        assertThat(expectedBytes).isNotNull()
        assertThat(actualBytes!!.contentEquals(expectedBytes!!)).isTrue()
    }

    private suspend fun getDrmLicense(licenseUrl: String, payload: ByteArray) =
        drmLicenseRepository.getDrmLicense(licenseUrl, payload)
}
