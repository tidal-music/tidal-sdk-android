package com.tidal.sdk.player.commonandroid

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

internal class Base64CodecTest {

    private val base64Codec = Base64Codec()

    private val encodedByteArray =
        byteArrayOf(0x61, 0x48, 0x52, 0x30, 0x63, 0x44, 0x6f, 0x76, 0x4c, 0x77, 0x3d, 0x3d)
    private val decodedByteArray = byteArrayOf(0x68, 0x74, 0x74, 0x70, 0x3a, 0x2f, 0x2f)

    @Test
    fun ensureBase64DecoderReturnsCorrect() {
        val actual = base64Codec.decode(encodedByteArray)

        assertThat(actual).isEqualTo(decodedByteArray)
    }

    @Test
    fun ensureBase64EncoderReturnsCorrect() {
        val actual = base64Codec.encode(decodedByteArray)

        assertThat(actual).isEqualTo(encodedByteArray)
    }
}
