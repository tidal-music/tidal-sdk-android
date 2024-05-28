package com.tidal.sdk.player.events

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.player.commonandroid.jwt.Base64JwtDecoder
import com.tidal.sdk.player.events.model.User
import kotlin.properties.Delegates
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class UserSupplier(
    private val base64JwtDecoder: Base64JwtDecoder,
    private val credentialsProvider: CredentialsProvider,
    private val userClientIdSupplier: (() -> Int)?,
) {

    private var accessToken: String by Delegates.observable("") { _, oldValue, newValue ->
        if (oldValue != newValue) {
            claims = base64JwtDecoder.getClaims(newValue)
        }
    }

    private var claims: JsonObject? = null

    suspend operator fun invoke(): User {
        accessToken = credentialsProvider.getCredentials().successData?.token.orEmpty()
        return User(
            claims?.get("uid")?.jsonPrimitive?.content?.toLong() ?: -1,
            userClientIdSupplier?.invoke(),
            claims?.get("sid")?.jsonPrimitive?.content ?: "",
        )
    }
}
