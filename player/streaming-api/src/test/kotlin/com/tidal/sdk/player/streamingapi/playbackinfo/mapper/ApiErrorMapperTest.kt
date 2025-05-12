package com.tidal.sdk.player.streamingapi.playbackinfo.mapper

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.model.ApiError
import okhttp3.ResponseBody
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

/**
 * Test that the [ApiErrorMapper] returns correct based on different inputs. This is useful because
 * we then know what inputs we can expect to work.
 */
internal class ApiErrorMapperTest {

    private val apiErrorFactory = mock<ApiError.Factory>()

    private val apiErrorMapper = ApiErrorMapper(apiErrorFactory)

    @Test
    fun map() {
        val errorBodyString = "I'm an error body"
        val errorBody = mock<ResponseBody> { on { string() } doReturn errorBodyString }
        val response = mock<Response<*>> { on { it.errorBody() } doReturn errorBody }
        val original = mock<HttpException> { on { it.response() } doReturn response }
        val expected = mock<RuntimeException>()
        whenever(apiErrorFactory.fromJsonStringOrCause(errorBodyString, original))
            .thenReturn(expected)

        val actual = apiErrorMapper.map(original)

        verify(original).response()
        verify(response).errorBody()
        verify(errorBody).string()
        verify(apiErrorFactory).fromJsonStringOrCause(errorBodyString, original)
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(errorBody, response, original, expected, apiErrorFactory)
    }
}
