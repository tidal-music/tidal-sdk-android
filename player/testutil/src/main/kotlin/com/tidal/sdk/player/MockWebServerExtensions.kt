package com.tidal.sdk.player

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.buffer
import okio.source

private const val RESOURCE_DIR = "api-responses"

object MockWebServerExtensions {

    fun MockWebServer.enqueueResponse(fileName: String, code: Int = 200) {
        enqueue(mockResponse(fileName, code))
    }

    fun MockWebServer.mockResponse(fileName: String, code: Int = 200): MockResponse {
        val inputStream = javaClass.classLoader.getResourceAsStream("$RESOURCE_DIR/$fileName")
        val source = inputStream!!.source().buffer()
        val body = source.readString(Charsets.UTF_8)

        return MockResponse()
            .setResponseCode(code)
            .setBody(body)
    }

    fun MockWebServer.mockResponseAsBuffer(fileName: String): MockResponse {
        val buffer = Buffer()
        javaClass.classLoader.getResourceAsStream("raw/$fileName")?.use { inputStream ->
            buffer.use {
                it.writeAll(inputStream.source())
            }
        }

        return MockResponse().setBody(buffer)
    }
}
