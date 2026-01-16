package com.tidal.sdk.player.streamingapi

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object DrmLicenseFactory {

    private val octetStream = "application/octet-stream".toMediaType()

    fun default(): Response<ResponseBody> {
        val responseBody =
            ApiConstants.DRM_PAYLOAD_RESPONSE.toByteArray().toResponseBody(octetStream)
        return Response.success(responseBody)
    }

    fun emptyPayload(): Response<ResponseBody> {
        val responseBody = "".toByteArray().toResponseBody(octetStream)
        return Response.success(responseBody)
    }
}
