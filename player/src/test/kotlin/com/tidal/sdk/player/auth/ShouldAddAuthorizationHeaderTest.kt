package com.tidal.sdk.player.auth

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class ShouldAddAuthorizationHeaderTest {

    val shouldAddAuthorizationHeader = ShouldAddAuthorizationHeader()

    @ParameterizedTest
    @ValueSource(strings = ["api.tidal.com", "fsu.fa.tidal.com"])
    fun `should return true if host should add auth header`(host: String) {
        assertThat(shouldAddAuthorizationHeader(host)).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["sp-ad-cf.audio.tidal.com", "example.com", "hello"])
    fun `should return false if host should not send header`(host: String) {
        assertThat(shouldAddAuthorizationHeader(host)).isFalse()
    }
}
