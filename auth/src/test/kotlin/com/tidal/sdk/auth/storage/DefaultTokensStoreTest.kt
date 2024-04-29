package com.tidal.sdk.auth.storage

import android.content.SharedPreferences
import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.util.makeCredentials
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultTokensStoreTest {
    private val testCredentialsKey = "testKey"
    private var isPrefsEmpty = true
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var tokens: Tokens
    private lateinit var defaultTokensStore: DefaultTokensStore

    @BeforeEach
    fun setUp() {
        sharedPreferences = mockk()
        editor = mockk(relaxed = true)
        tokens = Tokens(
            credentials = makeCredentials(isExpired = false),
            refreshToken =
            "refreshToken"
        )

        every { sharedPreferences.edit() } returns editor
        every { editor.putString(any(), any()) } answers {
            isPrefsEmpty = false
            editor
        }
        every { editor.clear() } answers {
            isPrefsEmpty = true
            editor
        }
        every { sharedPreferences.getString(any(), any()) } answers {
            if (isPrefsEmpty) null else Json.encodeToString(tokens)
        }

        defaultTokensStore = DefaultTokensStore(testCredentialsKey, sharedPreferences)
    }

    @Test
    fun `getLatestTokens returns correct tokens when key matches`() {
        // given
        isPrefsEmpty = false

        // when
        val result = defaultTokensStore.getLatestTokens(testCredentialsKey)

        assert(result == tokens)
    }

    @Test
    fun `saveTokens correctly saves tokens to shared preferences`() {
        // when
        defaultTokensStore.saveTokens(tokens)

        // then
        verify { editor.putString(any(), any()) }
        verify { editor.apply() }
        assertEquals(
            defaultTokensStore.getLatestTokens(testCredentialsKey),
            tokens,
            "Tokens passed in for saving should be served when calling getLatestTokens."
        )
    }

    @Test
    fun `eraseTokens correctly erases tokens from shared preferences`() {
        // when
        defaultTokensStore.eraseTokens()

        // then
        verify { editor.clear() }
        verify { editor.apply() }
        assertEquals(
            defaultTokensStore.getLatestTokens(testCredentialsKey),
            null,
            "Tokens should be null after erasing."
        )
    }
}
