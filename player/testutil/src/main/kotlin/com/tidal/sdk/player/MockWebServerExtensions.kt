package com.tidal.sdk.player

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

private const val RESOURCE_DIR = "api-responses"

object MockWebServerExtensions {

    fun MockWebServer.enqueueResponse(fileName: String, code: Int = 200) {
        enqueue(MockResponse().setResponseCode(code).setBodyFromFile("$RESOURCE_DIR/$fileName"))
    }
}
