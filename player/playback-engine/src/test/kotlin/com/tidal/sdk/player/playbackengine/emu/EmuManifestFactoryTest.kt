package com.tidal.sdk.player.playbackengine.emu

import assertk.assertThat
import assertk.assertions.isSameInstanceAs
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

internal class EmuManifestFactoryTest {

    private val gson = mock<Gson>()
    private val base64Codec = mock<Base64Codec>()
    private val emuManifestFactory = EmuManifestFactory(gson, base64Codec)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(gson, base64Codec)

    @Test
    fun createShouldReturnParsedManifest() {
        val decodedManifest = "test".toByteArray()
        whenever(base64Codec.decode(ENCODED_MANIFEST.toByteArray(CHARSET)))
            .thenReturn(decodedManifest)
        val expectedEmuManifest = mock<EmuManifest>()
        whenever(gson.fromJson(decodedManifest.toString(CHARSET), EmuManifest::class.java))
            .thenReturn(expectedEmuManifest)

        val actualEmuManifest = emuManifestFactory.create(ENCODED_MANIFEST, CHARSET)

        assertThat(actualEmuManifest).isSameInstanceAs(expectedEmuManifest)
        verify(base64Codec).decode(ENCODED_MANIFEST.toByteArray(CHARSET))
        verify(gson).fromJson(decodedManifest.toString(CHARSET), EmuManifest::class.java)
        verifyNoInteractions(expectedEmuManifest)
    }
}
