package com.tidal.sdk.player.streamingprivileges.connection

import com.tidal.sdk.player.common.RequestBuilderFactory
import okhttp3.Request

internal class WebSocketConnectionRequestFactory(
    private val requestBuilderFactory: RequestBuilderFactory
) {

    fun create(url: String): Request = requestBuilderFactory.create().url(url).build()
}
