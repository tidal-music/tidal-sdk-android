package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.common.C
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.drm.ExoMediaDrm
import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import com.tidal.sdk.player.playbackengine.StreamingApiRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class TidalMediaDrmCallbackTest {

    private val streamingApiRepository = mock<StreamingApiRepository>()
    private val streamingSessionId = "test-streaming-session-id"
    private val licenseUrl = "test-license-url"
    private val drmMode = DrmMode.Streaming
    private val okHttpClient = mock<OkHttpClient>()
    private val provisionRequestBuilder = mock<Request.Builder>()
    private val provisionRequestBody = mock<RequestBody>()
    private val tidalMediaDrmCallback =
        TidalMediaDrmCallback(
            streamingApiRepository,
            streamingSessionId,
            licenseUrl,
            drmMode,
            okHttpClient,
            lazy { provisionRequestBuilder },
            lazy { provisionRequestBody },
            emptyMap(),
        )

    @Test
    fun executeKeyRequestWithDrmModeStreamingAndResponseSuccess() = runBlocking {
        val request =
            mock<ExoMediaDrm.KeyRequest> { on { data } doReturn "keyRequest".toByteArray() }
        val expectedDrmLicensePayload = "expectedDrmLicensePayload".toByteArray()
        val mockResponseBody =
            mock<ResponseBody> { on { bytes() } doReturn expectedDrmLicensePayload }
        val mockResponse =
            mock<retrofit2.Response<ResponseBody>> { on { body() } doReturn mockResponseBody }
        whenever(
                streamingApiRepository.getDrmLicense(
                    licenseUrl,
                    request.data,
                    streamingSessionId,
                    emptyMap(),
                )
            )
            .thenReturn(mockResponse)

        val actual = tidalMediaDrmCallback.executeKeyRequest(C.WIDEVINE_UUID, request)

        assertThat(actual).isSameInstanceAs(expectedDrmLicensePayload)
    }

    @Test
    fun executeKeyRequestWithDrmModeStreamingAndResponseError() = runBlocking {
        val request =
            mock<ExoMediaDrm.KeyRequest> { on { data } doReturn "keyRequest".toByteArray() }
        val expected = mock<RuntimeException>()
        whenever(
                streamingApiRepository.getDrmLicense(
                    licenseUrl,
                    request.data,
                    streamingSessionId,
                    emptyMap(),
                )
            )
            .thenThrow(expected)

        val actual =
            assertThrows<RuntimeException> {
                tidalMediaDrmCallback.executeKeyRequest(C.WIDEVINE_UUID, request)
            }
        assertThat(actual).isSameInstanceAs(expected)
    }

    @Test
    fun executeProvisionRequest() {
        val request =
            mock<ExoMediaDrm.ProvisionRequest> {
                on { defaultUrl } doReturn "http://example.com"
                on { data } doReturn "keyRequest".toByteArray()
            }
        val url = request.defaultUrl + "&signedRequest=" + Util.fromUtf8Bytes(request.data)
        val expectedByteArray = "okHttpResponse".toByteArray()
        val body = mock<ResponseBody> { on { bytes() } doReturn expectedByteArray }
        val response = mock<Response> { on { it.body } doReturn body }
        val call = mock<Call> { on { execute() } doReturn response }
        val provisioningRequest = mock<Request>()
        whenever(provisionRequestBuilder.url(url)).thenReturn(provisionRequestBuilder)
        whenever(provisionRequestBuilder.post(provisionRequestBody))
            .thenReturn(provisionRequestBuilder)
        whenever(provisionRequestBuilder.build()).thenReturn(provisioningRequest)
        whenever(okHttpClient.newCall(provisioningRequest)).thenReturn(call)

        val actual = tidalMediaDrmCallback.executeProvisionRequest(C.WIDEVINE_UUID, request)

        assertThat(actual).isSameInstanceAs(expectedByteArray)
    }
}
