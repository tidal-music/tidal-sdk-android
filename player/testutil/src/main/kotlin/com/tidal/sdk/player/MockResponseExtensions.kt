package com.tidal.sdk.player

import okhttp3.mockwebserver.MockResponse
import okio.Buffer
import okio.source

fun MockResponse.setBodyFromFile(fileName: String): MockResponse {
    val buffer = Buffer()
    javaClass.classLoader.getResourceAsStream(fileName)?.use { inputStream ->
        buffer.use { it.writeAll(inputStream.source()) }
    }
    return setBody(buffer)
}
