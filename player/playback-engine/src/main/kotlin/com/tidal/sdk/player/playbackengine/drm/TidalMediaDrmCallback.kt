package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.common.util.Util
import androidx.media3.exoplayer.drm.ExoMediaDrm
import androidx.media3.exoplayer.drm.MediaDrmCallback
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.playbackengine.StreamingApiRepository
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
import java.util.UUID
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

/**
 * Performs [ExoMediaDrm] key and provisioning requests. Provisioning request are standard, but the
 * key request takes a round trip to Tidal's license server for getting the proper drm licenses.
 *
 * @param[streamingApiRepository] A repository for getting the drm license.
 * @param[base64Codec] A string codec for encoding the request data and decoding the response
 *   payload.
 * @param[drmLicenseRequestFactory] A factory for creating [DrmLicenseRequest]s.
 * @param[mode] The [DrmMode] for what action this particular request is for.
 * @param[okHttpClient] The [OkHttpClient] to be used by the provisioning request.
 * @param[provisionRequestBuilder] The [Request.Builder] to be used to create the provision request.
 * @param[provisionRequestBody] The [RequestBody] to be used as body for the provisioning post
 *   request.
 */
@Suppress("LongParameterList")
internal class TidalMediaDrmCallback(
    private val streamingApiRepository: StreamingApiRepository,
    private val base64Codec: Base64Codec,
    private val drmLicenseRequestFactory: DrmLicenseRequestFactory,
    private val mode: DrmMode,
    private val okHttpClient: OkHttpClient,
    private val provisionRequestBuilder: Lazy<Request.Builder>,
    private val provisionRequestBody: Lazy<RequestBody>,
    private val extras: Extras?,
) : MediaDrmCallback {

    override fun executeKeyRequest(uuid: UUID, request: ExoMediaDrm.KeyRequest): ByteArray {
        val encodedRequestData = base64Codec.encode(request.data)

        val charset = Charsets.UTF_8
        val drmLicenseRequest =
            drmLicenseRequestFactory.create(encodedRequestData.toString(charset))

        val drmLicense =
            when (mode) {
                DrmMode.Streaming ->
                    runBlocking { streamingApiRepository.getDrmLicense(drmLicenseRequest, extras) }
            }

        return base64Codec.decode(drmLicense.payload.toByteArray(charset))
    }

    override fun executeProvisionRequest(
        uuid: UUID,
        request: ExoMediaDrm.ProvisionRequest,
    ): ByteArray {
        val url = request.defaultUrl + "&signedRequest=" + Util.fromUtf8Bytes(request.data)

        val provisioningRequest =
            provisionRequestBuilder.value.url(url).post(provisionRequestBody.value).build()

        val response = okHttpClient.newCall(provisioningRequest).execute()

        return response.body?.bytes() ?: byteArrayOf()
    }
}
