package com.tidal.sdk.player.commonandroid.jwt

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.tidal.sdk.player.commonandroid.Base64Codec
import kotlinx.serialization.json.Json
import org.junit.Test

private const val TOKEN =
    "eyJraWQiOiJ2OU1GbFhqWSIsImFsZyI6IkVTMjU2In0.eyJ0eXBlIjoibzJfYWNjZXNzIiwidWlkIjoxNjI5MDc1NDAsInNjb3BlIjoid19zdWIgcl91c3Igd191c3IiLCJnVmVyIjowLCJzVmVyIjowLCJjaWQiOjYzNDMsImN1ayI6ImpPSXo2QUhMUWtUSE5Cak9feGFkbWluIiwiZXhwIjoxNzI0ODMyOTY2LCJzaWQiOiIwOTljZjA1OS1mYzI0LTRmYzYtYjVjOS1iMDY4M2RlMjU0MzIiLCJpc3MiOiJodHRwczovL2F1dGgudGlkYWwuY29tL3YxIn0.MPIQUWuQ7I-116oQtLOqqfgIaW01dRw4Ff9zq2iD1K5n2YUjwzfLkpAEsJPPcMoB2jC9q5wdbxZCPBjqetQ30A"

class Base64JwtDecoderTest {

    private val base64Codec = Base64Codec()
    private val base64JwtDecoder = Base64JwtDecoder(base64Codec)

    @Test
    fun getClaims() {
        val expected = "{\n" +
            "  \"type\": \"o2_access\",\n" +
            "  \"uid\": 162907540,\n" +
            "  \"scope\": \"w_sub r_usr w_usr\",\n" +
            "  \"gVer\": 0,\n" +
            "  \"sVer\": 0,\n" +
            "  \"cid\": 6343,\n" +
            "  \"cuk\": \"jOIz6AHLQkTHNBjO_xadmin\",\n" +
            "  \"exp\": 1724832966,\n" +
            "  \"sid\": \"099cf059-fc24-4fc6-b5c9-b0683de25432\",\n" +
            "  \"iss\": \"https://auth.tidal.com/v1\"\n" +
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
