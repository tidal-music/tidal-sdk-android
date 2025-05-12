package com.tidal.sdk.auth.storage.legacycredentials

import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.common.e
import com.tidal.sdk.common.i
import com.tidal.sdk.common.logger
import kotlinx.serialization.json.Json

internal class LegacyCredentialsMigrator {

    private inline fun <reified T : LegacyTokens> decodeJsonString(jsonString: String): Tokens =
        Json.decodeFromString<T>(jsonString).toTokens()

    @Suppress("TooGenericExceptionCaught")
    fun migrateCredentials(jsonString: String): Tokens {
        // if ever necessary, add further legacy type operations here
        val operations =
            listOf(
                { decodeJsonString<TokensV1>(jsonString) },
                { decodeJsonString<TokensV2>(jsonString) },
            )
        logger.i { "Attempting to decode using legacy types." }
        val exceptions = mutableListOf<Exception>()
        for (operation in operations) {
            try {
                return operation()
            } catch (e: Exception) {
                logger.i { "Failed to decode using legacy types." }
                exceptions.plus(e)
            }
        }
        logger.e { "Failed to decode using legacy types! Exceptions caught:" }
        exceptions.forEach { logger.e { it } }
        throw exceptions.first()
    }
}
