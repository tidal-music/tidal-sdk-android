package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.tidalapi.generated.models.ArtworksResourceObject
import com.tidal.sdk.tidalapi.generated.models.ArtworksSingleResourceDataDocument
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.Response
import retrofit2.http.GET

class SkipUnknownIncludedConverterFactoryTest {

    private lateinit var server: MockWebServer

    private interface TestApi {
        @GET("/artworks/1") suspend fun artwork(): Response<ArtworksSingleResourceDataDocument>
    }

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    private suspend fun fetch(body: String): ArtworksSingleResourceDataDocument? {
        server.enqueue(MockResponse().setHeader("Content-Type", "application/json").setBody(body))
        return RetrofitProvider(retryPolicy = null)
            .provideRetrofit(server.url("/").toString(), FakeCredentialsProvider())
            .create(TestApi::class.java)
            .artwork()
            .body()
    }

    private fun document(
        data: String = """{"id":"1","type":"artworks"}""",
        vararg included: String,
    ) = """{"data":$data,"links":{"self":"/artworks/1"},"included":[${included.joinToString()}]}"""

    @Test
    fun `drops included resources of an unknown type`() = runTest {
        val body = document(included = arrayOf(ARTWORK, """{"id":"3","type":"holograms"}"""))

        val result = fetch(body)

        assertEquals(listOf("2"), result?.included?.map { (it as ArtworksResourceObject).id })
    }

    @Test
    fun `drops included resources with an unknown value`() = runTest {
        val unknownMediaType =
            """{"id":"3","type":"artworks","attributes":{"files":[],"mediaType":"HOLOGRAM"}}"""

        val result = fetch(document(included = arrayOf(ARTWORK, unknownMediaType)))

        assertEquals(listOf("2"), result?.included?.map { (it as ArtworksResourceObject).id })
    }

    @Test
    fun `still fails when the primary data does not decode`() = runTest {
        val unknownMediaType =
            """{"id":"1","type":"artworks","attributes":{"files":[],"mediaType":"HOLOGRAM"}}"""

        assertThrows<SerializationException> {
            fetch(document(data = unknownMediaType, """{"id":"3","type":"holograms"}"""))
        }
    }

    private companion object {
        const val ARTWORK = """{"id":"2","type":"artworks"}"""
    }
}
