package com.tidal.sdk.player.playbackengine.bts

import assertk.assertThat
import assertk.assertions.isSameAs
import com.google.gson.Gson
import com.tidal.sdk.player.commonandroid.Base64Codec
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

private const val ENCODED_MANIFEST = "encodedManifest"
private val CHARSET = Charsets.ISO_8859_1

internal class BtsManifestFactoryTest {

    private val gson = mock<Gson>()
    private val base64Codec = mock<Base64Codec>()
    private val btsManifestFactory = DefaultBtsManifestFactory(gson, base64Codec)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(gson, base64Codec)

    @Test
    fun createShouldReturnParsedManifest() {
        val decodedManifest = "test".toByteArray()
        whenever(base64Codec.decode(ENCODED_MANIFEST.toByteArray(CHARSET)))
            .thenReturn(decodedManifest)
        val expectedBtsManifest = mock<BtsManifest>()
        whenever(
            gson.fromJson(
                decodedManifest.toString(CHARSET),
                BtsManifest::class.java,
            ),
        ).thenReturn(expectedBtsManifest)

        val actualBtsManifest = btsManifestFactory.create(ENCODED_MANIFEST, CHARSET)

        assertThat(actualBtsManifest).isSameAs(expectedBtsManifest)
        verify(base64Codec).decode(ENCODED_MANIFEST.toByteArray(CHARSET))
        verify(gson).fromJson(
            decodedManifest.toString(CHARSET),
            BtsManifest::class.java,
        )
        verifyNoInteractions(expectedBtsManifest)
    }
}
