package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.tidalapi.generated.models.ArtistsCreateSingleResourceDataDocument
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import retrofit2.http.POST

class CreateDryRunResponseCompatibilityTest {

    private lateinit var server: MockWebServer

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    private interface TestApi {
        @POST("artists")
        suspend fun createArtist(): Response<ArtistsCreateSingleResourceDataDocument>
    }

    private fun api(): TestApi =
        RetrofitProvider(retryPolicy = null)
            .provideRetrofit(server.url("/").toString(), FakeCredentialsProvider())
            .create(TestApi::class.java)

    @Test
    fun `accepts generic JSON API success documents for 200 and 201`() = runTest {
        listOf(200, 201).forEach { status ->
            server.enqueue(
                MockResponse()
                    .setResponseCode(status)
                    .setHeader("Content-Type", "application/vnd.api+json")
                    .setBody("""{"links":{"self":"/artists"},"meta":{}}""")
            )

            val response = api().createArtist()

            assertEquals(status, response.code())
            assertNull(response.body())
        }
    }

    @Test
    fun `continues to decode an ordinary typed create response`() = runTest {
        server.enqueue(
            MockResponse()
                .setResponseCode(201)
                .setHeader("Content-Type", "application/vnd.api+json")
                .setBody(
                    """
                    {
                      "data": {"id": "123", "type": "artists"},
                      "links": {"self": "/artists/123"}
                    }
                    """
                        .trimIndent()
                )
        )

        val response = api().createArtist()

        assertEquals(201, response.code())
        assertEquals("123", response.body()?.data?.id)
    }

    @Test
    fun `does not hide a malformed typed create response`() = runTest {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/vnd.api+json")
                .setBody("""{"data":{"type":"artists"},"links":{"self":"/artists"}}""")
        )

        val result = runCatching { api().createArtist() }

        assertFalse(result.isSuccess)
        assertNotNull(result.exceptionOrNull())
    }
}
