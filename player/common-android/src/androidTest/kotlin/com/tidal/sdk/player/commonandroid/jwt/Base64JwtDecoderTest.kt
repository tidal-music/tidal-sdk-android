package com.tidal.sdk.player.commonandroid.jwt

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.tidal.sdk.player.commonandroid.Base64Codec
import kotlinx.serialization.json.Json
import org.junit.Test

private const val TOKEN =
    "eyJraWQiOiJ2OU1GbFhqWSIsImFsZyI6IkVTMjU2In0.eyJ0eXBlIjoidGVzdCIsInVpZCI6MTIzLCJnVmVyIjowLCJzVmVyIjowLCJjaWQiOjEyMywiY3VrIjoiYWJjIiwiZXhwIjoxMjMsInNpZCI6ImFiZC0xMjMifQ==.MPIQUWuQ7I-116oQtLOqqfgIaW01dRw4Ff9zq2iD1K5n2YUjwzfLkpAEsJPPcMoB2jC9q5wdbxZCPBjqetQ30A"

class Base64JwtDecoderTest {

    private val base64Codec = Base64Codec()
    private val base64JwtDecoder = Base64JwtDecoder(base64Codec)

    @Test
    fun getClaims() {
        val expected =
            "{\n" +
                "  \"type\": \"test\",\n" +
                "  \"uid\": 123,\n" +
                "  \"gVer\": 0,\n" +
                "  \"sVer\": 0,\n" +
                "  \"cid\": 123,\n" +
                "  \"cuk\": \"abc\",\n" +
                "  \"exp\": 123,\n" +
                "  \"sid\": \"abd-123\"\n" +
                "}"

        val actualClaim = base64JwtDecoder.getClaims(TOKEN)

        assertThat(actualClaim).isEqualTo(Json.parseToJsonElement(expected))
    }

    @Test
    fun getClaimsWithEmptyToken() {
        val token = ""

        val actualClaim = base64JwtDecoder.getClaims(token)

        assertThat(actualClaim).isNull()
    }

    @Test
    fun getClaimsWithInvalidToken() {
        val token = "1234fas"

        val actualClaim = base64JwtDecoder.getClaims(token)

        assertThat(actualClaim).isNull()
    }
}
