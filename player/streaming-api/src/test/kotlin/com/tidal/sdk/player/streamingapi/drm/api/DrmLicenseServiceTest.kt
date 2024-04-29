package com.tidal.sdk.player.streamingapi.drm.api

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isInstanceOf
import com.tidal.sdk.player.MockWebServerExtensions.enqueueResponse
import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.player.streamingapi.DrmLicenseFactory
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicense
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Test that the [DrmLicenseService] returns correct [DrmLicense] in various situations, or
 * that it fails with an exception.
 */
internal class DrmLicenseServiceTest {

    @get:ExtendWith
    val server = MockWebServer()

    private lateinit var drmLicenseService: DrmLicenseService

    private val converterFactory = GsonConverterFactory.create()

    @BeforeEach
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(converterFactory)
            .build()

        drmLicenseService = retrofit.create(DrmLicenseService::class.java)
    }

    @Test
    fun getWidevineLicenseShouldFailWhenNetworkError() {
        server.enqueue(MockResponse().throttleBody(1024, 1, TimeUnit.SECONDS))

        assertFailure { getWidevineLicense() }
            .isInstanceOf(IOException::class.java)
    }

    @ParameterizedTest
    @ValueSource(ints = [400, 404, 500])
    fun getWidevineLicenseShouldFailWhenNonOkStatus(status: Int) {
        server.enqueue(MockResponse().setResponseCode(status))

        assertFailure { getWidevineLicense() }
            .isInstanceOf(HttpException::class.java)
    }

    @Test
    fun getWidevineLicenseShouldReturnCorrect() {
        server.enqueueResponse("${ApiConstants.LICENSE_PATH}.json")

        val widevineLicense = getWidevineLicense()

        assertThat(widevineLicense)
            .isDataClassEqualTo(DrmLicenseFactory.default())
    }

    @Test
    fun getWidevineLicenseShouldReturnCorrectWhenStreamingSessionIdIsEmpty() {
        server.enqueueResponse("${ApiConstants.LICENSE_PATH}_empty_streaming_session_id.json")

        val widevineLicense = getWidevineLicense()

        assertThat(widevineLicense)
            .isDataClassEqualTo(DrmLicenseFactory.emptyStreamingSessionId())
    }

    @Test
    fun getWidevineLicenseShouldReturnCorrectWhenPayloadIsEmpty() {
        server.enqueueResponse("${ApiConstants.LICENSE_PATH}_empty_payload.json")

        val widevineLicense = getWidevineLicense()

        assertThat(widevineLicense)
            .isDataClassEqualTo(DrmLicenseFactory.emptyPayload())
    }

    private fun getWidevineLicense() = runBlocking {
        drmLicenseService.getWidevineLicense(
            DrmLicenseRequest(
                ApiConstants.STREAMING_SESSION_ID,
                ApiConstants.LICENSE_SECURITY_TOKEN,
                ApiConstants.DRM_PAYLOAD_REQUEST,
            ),
        )
    }
}
