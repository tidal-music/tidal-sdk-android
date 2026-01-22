package com.tidal.sdk.player.streamingapi.drm.api

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.player.streamingapi.DrmLicenseFactory
import java.net.ConnectException
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Test that the [DrmLicenseService] returns correct [Response]<[ResponseBody]> in various
 * situations, or that it fails with an exception.
 */
internal class DrmLicenseServiceTest {

    @get:ExtendWith val server = MockWebServer()

    private lateinit var drmLicenseService: DrmLicenseService

    private val converterFactory = GsonConverterFactory.create()

    @BeforeEach
    fun setUp() {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(converterFactory)
                .build()

        drmLicenseService = retrofit.create(DrmLicenseService::class.java)
    }

    @Test
    fun getWidevineLicenseShouldFailWhenNetworkError() {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        assertThrows<ConnectException> { getWidevineLicense() }
    }

    @ParameterizedTest
    @ValueSource(ints = [400, 404, 500])
    fun getWidevineLicenseShouldFailWhenNonOkStatus(status: Int) {
        server.enqueue(MockResponse().setResponseCode(status))

        val response = getWidevineLicense()

        assertThat(response.isSuccessful).isEqualTo(false)
        assertThat(response.code()).isEqualTo(status)
    }

    @Test
    fun getWidevineLicenseShouldReturnCorrect() {
        server.enqueue(
            MockResponse().setResponseCode(200).setBody(ApiConstants.DRM_PAYLOAD_RESPONSE)
        )

        val widevineLicense = getWidevineLicense()
        val expected = DrmLicenseFactory.default()

        assertThat(widevineLicense.isSuccessful).isEqualTo(expected.isSuccessful)
        val actualBytes = widevineLicense.body()?.bytes()
        val expectedBytes = expected.body()?.bytes()

        assertThat(actualBytes).isNotNull()
        assertThat(expectedBytes).isNotNull()
        assertThat(actualBytes!!.contentEquals(expectedBytes!!)).isTrue()
    }

    @Test
    fun getWidevineLicenseShouldReturnCorrectWhenPayloadIsEmpty() {
        server.enqueue(MockResponse().setResponseCode(200).setBody(""))

        val widevineLicense = getWidevineLicense()
        val expected = DrmLicenseFactory.emptyPayload()

        assertThat(widevineLicense.isSuccessful).isEqualTo(expected.isSuccessful)
        val actualBytes = widevineLicense.body()?.bytes()
        val expectedBytes = expected.body()?.bytes()

        assertThat(actualBytes).isNotNull()
        assertThat(expectedBytes).isNotNull()
        assertThat(actualBytes!!.contentEquals(expectedBytes!!)).isTrue()
    }

    private fun getWidevineLicense() = runBlocking {
        val requestBody =
            RequestBody.create(
                "application/octet-stream".toMediaTypeOrNull(),
                ApiConstants.DRM_PAYLOAD_REQUEST,
            )
        drmLicenseService.getWidevineLicense(ApiConstants.LICENSE_URL, requestBody)
    }
}
