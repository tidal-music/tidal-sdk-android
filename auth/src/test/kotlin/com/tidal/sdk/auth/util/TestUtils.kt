package com.tidal.sdk.auth.util

import com.tidal.sdk.auth.model.ErrorResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

/** @return a [retrofit2.HttpException] with the submitted error code */
fun buildTestHttpException(code: Int): HttpException {
    val response = Response.error<String>(code, "response".toResponseBody())
    return HttpException(response)
}

/** @return a [retrofit2.HttpException] with the submitted error response */
internal fun buildTestHttpException(errorResponse: ErrorResponse): HttpException {
    val encoded = Json.encodeToString(errorResponse)
    val body = encoded.toResponseBody()
    val response = Response.error<String>(errorResponse.status, body)
    return HttpException(response)
}
