package com.tidal.sdk.player.playbackengine.dash

import android.net.Uri
import androidx.media3.exoplayer.dash.manifest.DashManifest
import androidx.media3.exoplayer.upstream.ParsingLoadable
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.commonandroid.Base64Codec
import java.io.ByteArrayInputStream
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

private const val ENCODED_MANIFEST = "encodedManifest"

internal class DashManifestFactoryTest {

    private val base64Codec = mock<Base64Codec>()
    private val dashManifestParser = mock<ParsingLoadable.Parser<DashManifest>>()
    private val dashManifestFactory = DashManifestFactory(base64Codec, dashManifestParser)

    @Test
    fun createShouldReturnParsedManifest() {
        val decodedManifest = "test".toByteArray()
        whenever(base64Codec.decode(ENCODED_MANIFEST.toByteArray(Charsets.ISO_8859_1)))
            .thenReturn(decodedManifest)
        val decodedManifestInputStream = mock<ByteArrayInputStream>()
        dashManifestFactory.reflectionSetByteArrayInputStreamF(
            mock { on { invoke(decodedManifest) } doReturn decodedManifestInputStream }
        )
        val expectedDashManifest = mock<DashManifest>()
        whenever(dashManifestParser.parse(Uri.EMPTY, decodedManifestInputStream))
            .thenReturn(expectedDashManifest)

        val actualDashManifest = dashManifestFactory.create(ENCODED_MANIFEST, Charsets.ISO_8859_1)

        assertThat(actualDashManifest).isSameAs(expectedDashManifest)
    }
}
