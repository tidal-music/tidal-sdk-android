package com.tidal.sdk.player.events

import android.app.UiModeManager
import android.content.Context
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.player.commonandroid.jwt.Base64JwtDecoder
import com.tidal.sdk.player.events.model.Client
import kotlin.properties.Delegates
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class ClientSupplier(
    private val context: Context,
    private val uiModeManager: UiModeManager,
    private val base64JwtDecoder: Base64JwtDecoder,
    private val credentialsProvider: CredentialsProvider,
    private val version: String,
) {

    private var accessToken: String by Delegates.observable("") { _, oldValue, newValue ->
        if (oldValue != newValue) {
            claims = base64JwtDecoder.getClaims(newValue)
        }
    }

    private var claims: JsonObject? = null

    operator fun invoke(): Client {
        accessToken = credentialsProvider.getLatestCredentials()?.token.orEmpty()
        return Client(
            claims?.get("cid")?.jsonPrimitive?.content ?: "",
            Client.DeviceType.from(context, uiModeManager),
            version,
        )
    }
}
