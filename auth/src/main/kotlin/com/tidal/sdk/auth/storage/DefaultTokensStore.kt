package com.tidal.sdk.auth.storage

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.auth.storage.legacycredentials.LegacyCredentialsMigrator
import javax.inject.Inject
import kotlinx.serialization.decodeFromString as decode
import kotlinx.serialization.encodeToString as encode
import kotlinx.serialization.json.Json

/**
 * This class uses [EncryptedSharedPreferences] to securely store credentials.
 * Pass in a [SharedPreferences] instance to use a custom one, by default
 * we inject an [EncryptedSharedPreferences] instance.
 */
internal class DefaultTokensStore @Inject constructor(
    private val credentialsKey: String,
    private val sharedPreferences: SharedPreferences,
) : TokensStore {

    private var latestTokens: Tokens? = null
        get() {
            return field ?: loadTokens()
        }

    private val encryptedSharedPreferences: SharedPreferences by lazy {
        sharedPreferences
    }

    override fun getLatestTokens(key: String): Tokens? {
        if (key != credentialsKey) return null
        return latestTokens
    }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    private fun loadTokens(): Tokens? = encryptedSharedPreferences.getString(
        credentialsKey,
        null,
    )?.let {
        try {
            Json.decode<Tokens>(it)
        } catch (e: Exception) {
            LegacyCredentialsMigrator().migrateCredentials(it)
        }.also { tokens ->
            saveTokens(tokens)
        }
    }

    override fun saveTokens(tokens: Tokens) {
        val stringToSave = Json.encode(tokens)
        encryptedSharedPreferences.edit().putString(credentialsKey, stringToSave).apply().also {
            latestTokens = tokens
        }
    }

    override fun eraseTokens() {
        encryptedSharedPreferences.edit().clear().apply().also {
            latestTokens = null
        }
    }
}
