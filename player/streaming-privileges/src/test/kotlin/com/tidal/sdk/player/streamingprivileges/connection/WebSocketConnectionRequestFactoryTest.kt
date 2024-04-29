package com.tidal.sdk.player.streamingprivileges.connection

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.RequestBuilderFactory
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class WebSocketConnectionRequestFactoryTest {

    private val requestBuilderFactory = mock<RequestBuilderFactory>()
    private val webSocketConnectionRequestFactory = WebSocketConnectionRequestFactory(
        requestBuilderFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(requestBuilderFactory)

    @Test
    fun createReturnsRequestWithUrl() {
        val url = "url"
        val expected = mock<Request>()
        val requestBuilder = mock<Request.Builder> {
            on { url(url) } doReturn it
            on { build() } doReturn expected
        }
        whenever(requestBuilderFactory.create()) doReturn requestBuilder

        val actual = webSocketConnectionRequestFactory.create(url)

        verify(requestBuilderFactory).create()
        inOrder(requestBuilder).apply {
            verify(requestBuilder).url(url)
            verify(requestBuilder).build()
        }
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(requestBuilder, expected)
    }
}
