package com.tidal.sdk.auth.model

import java.lang.IllegalStateException
import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Test

class ScopesTest {

    private val scopesRegex = Scopes.VALID_SCOPES_STRING_REGEX.toRegex()

    @Test
    fun `toString produces a valid string`() {
        // given
        val scopeSet = setOf("r_usr", "w_usr", "w_sub")
        val scopes = Scopes(scopeSet)

        // when
        val convertedString = scopes.toString()

        // then
        assert(scopesRegex.matches(convertedString)) {
            "Scopes.toString() should produce a usable string"
        }
    }

    @Test
    fun `Scopes creation fails if an element does not adhere to the expected format`() {
        assertFailsWith<IllegalStateException>(
            "Scopes creation should fail if an unexpected string is submitted",
        ) {
            // given
            val scopeSet = setOf("r_usr", "Hello, World!", "w_sub")

            // when
            Scopes(scopeSet)
        }
    }

    @Test
    fun `fromString produces a valid Scopes instance`() {
        // given
        val scopesString = "r_usr w_usr w_sub"

        // when
        val scopes = Scopes.fromString(scopesString)

        // then
        assert(scopes.scopes.size == 3) {
            "Scopes.fromString() should correctly interpret a valid string"
        }
    }

    @Test
    fun `fromString produces a valid Scopes instance from empty set`() {
        // given
        val scopesString = ""

        // when
        val scopes = Scopes.fromString(scopesString)

        // then
        assert(scopes.scopes.isEmpty()) {
            "Scopes.fromString() should correctly interpret a blank string"
        }
    }

    @Test
    fun `fromString fails with an error if the submitted string isn't valid`() {
        // given
        val wrongString1 = "r_usrw_usr w_sub"
        val wrongString2 = "r_usr,w_usr,w_sub"
        val wrongString3 = "rw_usr,w_usr,w_sub"
        val wrongString4 = "Hello, world!"

        // when
        var expectedFailures = 0
        setOf(wrongString1, wrongString2, wrongString3, wrongString4).forEach {
            try {
                Scopes.fromString(it)
            } catch (e: Exception) {
                expectedFailures += 1
            }
        }

        // then
        assert(expectedFailures == 4) {
            "Scopes.toString() should correctly fail on malformed scopes strings"
        }
    }
}
