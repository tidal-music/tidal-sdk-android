package com.tidal.sdk.auth.storage

import android.content.SharedPreferences
import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.auth.storage.legacycredentials.LegacyCredentialsV1
import com.tidal.sdk.auth.storage.legacycredentials.LegacyCredentialsV2
import com.tidal.sdk.auth.storage.legacycredentials.Scopes
import com.tidal.sdk.auth.storage.legacycredentials.TokensV1
import com.tidal.sdk.auth.storage.legacycredentials.TokensV2
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlin.test.Test
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach

class DefaultTokensStoreMigrationsTest {
    private val testCredentialsKey = "testKey"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var defaultTokensStore: DefaultTokensStore

    @BeforeEach
    fun setUp() {
        sharedPreferences = mockk()
        editor = mockk(relaxed = true)

        every { sharedPreferences.edit() } returns editor

        defaultTokensStore = DefaultTokensStore(testCredentialsKey, sharedPreferences)
    }

    @Test
    fun `getLatestTokens saves and returns Tokens when SharedPrefs returns LegacyCredentialsV1`() {
        // given
        val scopes = Scopes.fromString(setOf("scope1", "scope2").joinToString(""))
        val legacyCredentialsV1 =
            LegacyCredentialsV1(
                clientId = "clientId",
                requestedScopes = scopes,
                clientUniqueKey = "clientUniqueKey",
                grantedScopes = scopes,
                userId = "userId",
                expires = null,
                token = "token",
            )
        val refreshToken = "refreshToken"
        val legacyTokens = TokensV1(legacyCredentialsV1, refreshToken)
        val expected = Tokens(legacyCredentialsV1.toCredentials(), refreshToken)

        every { sharedPreferences.getString(any(), any()) } answers
            {
                Json.encodeToString(legacyTokens)
            }

        // when
        val result = defaultTokensStore.getLatestTokens(testCredentialsKey)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `getLatestTokens saves and returns Tokens when SharedPrefs returns LegacyCredentialsV2`() {
        // given
        val scopes = setOf("scope1", "scope2")
        val legacyCredentialsV2 =
            LegacyCredentialsV2(
                clientId = "clientId",
                requestedScopes = scopes,
                clientUniqueKey = "clientUniqueKey",
                grantedScopes = scopes,
                userId = "userId",
                expires = Instant.fromEpochSeconds(0),
                token = "token",
            )
        val refreshToken = "refreshToken"
        val legacyTokens = TokensV2(legacyCredentialsV2, refreshToken)
        val expected = Tokens(legacyCredentialsV2.toCredentials(), refreshToken)

        every { sharedPreferences.getString(any(), any()) } answers
            {
                Json.encodeToString(legacyTokens)
            }

        // when
        val result = defaultTokensStore.getLatestTokens(testCredentialsKey)

        // then
        assertEquals(expected, result)
    }
}
