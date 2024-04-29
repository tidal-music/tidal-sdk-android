package com.tidal.sdk.player.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class NonIntrusiveHttpLoggingInterceptorTest {

    private val basicLevelHttpLoggingInterceptor = mock<HttpLoggingInterceptor>()
    private val bodyLevelHttpLoggingInterceptor = mock<HttpLoggingInterceptor>()
    private val nonIntrusiveHttpLoggingInterceptor = NonIntrusiveHttpLoggingInterceptor(
        basicLevelHttpLoggingInterceptor,
        bodyLevelHttpLoggingInterceptor,
    )

    @Test
    fun interceptApiRequest() {
        val host = "api.tidal.com"
        val url = mock<HttpUrl> {
            on { it.host } doReturn host
        }
        val request = mock<Request> {
            on { it.url } doReturn url
        }
        val chain = mock<Chain> {
            on { request() } doReturn request
        }
        whenever(bodyLevelHttpLoggingInterceptor.intercept(chain)).thenReturn(mock())

        nonIntrusiveHttpLoggingInterceptor.intercept(chain)

        verify(bodyLevelHttpLoggingInterceptor).intercept(chain)
        verifyNoMoreInteractions(bodyLevelHttpLoggingInterceptor)
        verifyNoInteractions(basicLevelHttpLoggingInterceptor)
    }

    @Test
    fun interceptEventRequest() {
        val host = "et.tidal.com"
        val url = mock<HttpUrl> {
            on { it.host } doReturn host
        }
        val request = mock<Request> {
            on { it.url } doReturn url
        }
        val chain = mock<Chain> {
            on { request() } doReturn request
        }
        whenever(bodyLevelHttpLoggingInterceptor.intercept(chain)).thenReturn(mock())

        nonIntrusiveHttpLoggingInterceptor.intercept(chain)

        verify(bodyLevelHttpLoggingInterceptor).intercept(chain)
        verifyNoMoreInteractions(bodyLevelHttpLoggingInterceptor)
        verifyNoInteractions(basicLevelHttpLoggingInterceptor)
    }

    @Test
    fun interceptAudioRequest() {
        val host = "audio.tidal.com"
        val url = mock<HttpUrl> {
            on { it.host } doReturn host
        }
        val request = mock<Request> {
            on { it.url } doReturn url
        }
        val chain = mock<Chain> {
            on { request() } doReturn request
        }
        whenever(basicLevelHttpLoggingInterceptor.intercept(chain)).thenReturn(mock())

        nonIntrusiveHttpLoggingInterceptor.intercept(chain)

        verify(basicLevelHttpLoggingInterceptor).intercept(chain)
        verifyNoMoreInteractions(basicLevelHttpLoggingInterceptor)
        verifyNoInteractions(bodyLevelHttpLoggingInterceptor)
    }
}
