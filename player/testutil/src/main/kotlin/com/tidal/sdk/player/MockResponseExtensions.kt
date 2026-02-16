package com.tidal.sdk.player

import okhttp3.mockwebserver.MockResponse
import okio.Buffer
import okio.source

fun MockResponse.setBodyFromFile(fileName: String): MockResponse {
    val buffer = Buffer()
    val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
        ?: error("Resource not found: $fileName (classLoader: ${javaClass.classLoader})")
    inputStream.use { buffer.use { buf -> buf.writeAll(it.source()) } }
    return setBody(buffer)
}
