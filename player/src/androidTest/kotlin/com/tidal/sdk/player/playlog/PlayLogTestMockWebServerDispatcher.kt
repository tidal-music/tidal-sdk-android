package com.tidal.sdk.player.playlog

import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.QueueDispatcher
import okhttp3.mockwebserver.RecordedRequest

internal class PlayLogTestMockWebServerDispatcher(private val mockWebServer: MockWebServer) :
    Dispatcher() {
    val urlRecords: Map<HttpUrl, HttpUrl>
        get() = _urlRecords
    private val _urlRecords = mutableMapOf<HttpUrl, HttpUrl>()
    private val delegate = QueueDispatcher()
    private val recordedBehaviors = mutableMapOf<HttpUrl, (RecordedRequest) -> MockResponse>()

    override fun dispatch(request: RecordedRequest) = recordedBehaviors[request.requestUrl].let {
        when (it) {
            null -> delegate.dispatch(request)
            else -> it(request)
        }
    }

    operator fun set(targetUrl: HttpUrl, behavior: (RecordedRequest) -> MockResponse) =
        synchronized(this) {
            val mockWebServerUrl = mockWebServer.url("${targetUrl.host}/${targetUrl.encodedPath}")
            recordedBehaviors[mockWebServerUrl] = behavior
            _urlRecords[targetUrl] = mockWebServerUrl
        }
}
