package com.tidal.sdk.player.auth

internal class ShouldAddAuthorizationHeader {
    operator fun invoke(host: String) = arrayOf("api", "fsu").any { it in host }
}
