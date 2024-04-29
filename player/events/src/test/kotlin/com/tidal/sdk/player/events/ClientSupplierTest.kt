package com.tidal.sdk.player.events

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.player.commonandroid.jwt.Base64JwtDecoder
import com.tidal.sdk.player.events.model.Client
import com.tidal.sdk.player.events.model.reflectionDeviceType
import com.tidal.sdk.player.events.model.reflectionToken
import com.tidal.sdk.player.events.model.reflectionVersion
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.internal.verification.VerificationModeFactory.times
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class ClientSupplierTest {

    private val context = mock<Context>()
    private val uiModeManager = mock<UiModeManager>()
    private val base64JwtDecoder = mock<Base64JwtDecoder>()
    private val credentialsProvider = mock<CredentialsProvider>()

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(context, uiModeManager, base64JwtDecoder, credentialsProvider)

    @Test
    fun invokeWhenTV() {
        val version = "version"
        val clientSupplier =
            ClientSupplier(context, uiModeManager, base64JwtDecoder, credentialsProvider, version)
        val token = "token"
        val credentials = mock<Credentials> {
            on { it.token } doReturn token
        }
        val clientIdString = "123-abc"
        val clientId = mock<JsonPrimitive> {
            on { it.jsonPrimitive.content } doReturn clientIdString
        }
        val clientIdKey = "cid"
        val claims = mock<JsonObject> {
            on { it[clientIdKey] } doReturn clientId
        }
        whenever(credentialsProvider.getLatestCredentials()).thenReturn(credentials)
        whenever(base64JwtDecoder.getClaims(token)).thenReturn(claims)
        whenever(uiModeManager.currentModeType) doReturn Configuration.UI_MODE_TYPE_TELEVISION

        val actualClient = clientSupplier.invoke()

        assertThat(actualClient.reflectionToken).isSameAs(clientIdString)
        assertThat(actualClient.reflectionDeviceType).isEqualTo(Client.DeviceType.TV)
        assertThat(actualClient.reflectionVersion).isSameAs(version)
        verify(credentialsProvider).getLatestCredentials()
        verify(base64JwtDecoder).getClaims(token)
        verify(claims)[clientIdKey]
        verify(uiModeManager).currentModeType
        verify(credentials).token
        verifyNoMoreInteractions(credentials)
    }

    @Test
    fun invokeWhenAndroidAuto() {
        val version = "version"
        val clientSupplier =
            ClientSupplier(context, uiModeManager, base64JwtDecoder, credentialsProvider, version)
        val token = "token"
        val credentials = mock<Credentials> {
            on { it.token } doReturn token
        }
        val clientIdString = "123-abc"
        val clientId = mock<JsonPrimitive> {
            on { it.jsonPrimitive.content } doReturn clientIdString
        }
        val clientIdKey = "cid"
        val claims = mock<JsonObject> {
            on { it[clientIdKey] } doReturn clientId
        }
        whenever(credentialsProvider.getLatestCredentials()).thenReturn(credentials)
        whenever(base64JwtDecoder.getClaims(token)).thenReturn(claims)
        whenever(uiModeManager.currentModeType) doReturn Configuration.UI_MODE_TYPE_CAR

        val actualClient = clientSupplier.invoke()

        assertThat(actualClient.reflectionToken).isSameAs(clientIdString)
        assertThat(actualClient.reflectionDeviceType).isEqualTo(Client.DeviceType.ANDROID_AUTO)
        assertThat(actualClient.reflectionVersion).isSameAs(version)
        verify(credentialsProvider).getLatestCredentials()
        verify(base64JwtDecoder).getClaims(token)
        verify(claims)[clientIdKey]
        verify(uiModeManager, times(2)).currentModeType
        verify(credentials).token
        verifyNoMoreInteractions(credentials)
    }

    @Test
    fun invokeWhenTablet() {
        val resources = mock<Resources> {
            on { getBoolean(R.bool.is_tablet) } doReturn true
        }
        whenever(context.resources) doReturn resources
        val version = "version"
        val clientSupplier =
            ClientSupplier(context, uiModeManager, base64JwtDecoder, credentialsProvider, version)
        val token = "token"
        val credentials = mock<Credentials> {
            on { it.token } doReturn token
        }
        val clientIdString = "123-abc"
        val clientId = mock<JsonPrimitive> {
            on { it.jsonPrimitive.content } doReturn clientIdString
        }
        val clientIdKey = "cid"
        val claims = mock<JsonObject> {
            on { it[clientIdKey] } doReturn clientId
        }
        whenever(credentialsProvider.getLatestCredentials()).thenReturn(credentials)
        whenever(base64JwtDecoder.getClaims(token)).thenReturn(claims)

        val actualClient = clientSupplier.invoke()

        assertThat(actualClient.reflectionToken).isSameAs(clientIdString)
        assertThat(actualClient.reflectionDeviceType).isEqualTo(Client.DeviceType.TABLET)
        assertThat(actualClient.reflectionVersion).isSameAs(version)
        verify(credentialsProvider).getLatestCredentials()
        verify(base64JwtDecoder).getClaims(token)
        verify(claims)[clientIdKey]
        verify(context).resources
        verify(resources).getBoolean(R.bool.is_tablet)
        verify(uiModeManager, times(2)).currentModeType
        verify(credentials).token
        verifyNoMoreInteractions(resources, credentials)
    }

    @Test
    fun invokeWhenPhone() {
        val resources = mock<Resources> {
            on { getBoolean(R.bool.is_tablet) } doReturn false
        }
        whenever(context.resources) doReturn resources
        val version = "version"
        val clientSupplier =
            ClientSupplier(context, uiModeManager, base64JwtDecoder, credentialsProvider, version)
        val token = "token"
        val credentials = mock<Credentials> {
            on { it.token } doReturn token
        }
        val clientIdString = "123-abc"
        val clientId = mock<JsonPrimitive> {
            on { it.jsonPrimitive.content } doReturn clientIdString
        }
        val clientIdKey = "cid"
        val claims = mock<JsonObject> {
            on { it[clientIdKey] } doReturn clientId
        }
        whenever(credentialsProvider.getLatestCredentials()).thenReturn(credentials)
        whenever(base64JwtDecoder.getClaims(token)).thenReturn(claims)

        val actualClient = clientSupplier.invoke()

        assertThat(actualClient.reflectionToken).isSameAs(clientIdString)
        assertThat(actualClient.reflectionDeviceType).isEqualTo(Client.DeviceType.PHONE)
        assertThat(actualClient.reflectionVersion).isSameAs(version)
        verify(credentialsProvider).getLatestCredentials()
        verify(base64JwtDecoder).getClaims(token)
        verify(claims)[clientIdKey]
        verify(context).resources
        verify(resources).getBoolean(R.bool.is_tablet)
        verify(uiModeManager, times(2)).currentModeType
        verify(credentials).token
        verifyNoMoreInteractions(resources, credentials)
    }

    @Test
    fun invokeWhenPhoneWithEmptyToken() {
        val resources = mock<Resources> {
            on { getBoolean(R.bool.is_tablet) } doReturn false
        }
        whenever(context.resources) doReturn resources
        val version = "version"
        val clientSupplier =
            ClientSupplier(context, uiModeManager, base64JwtDecoder, credentialsProvider, version)
        val token = ""
        val credentials = mock<Credentials> {
            on { it.token } doReturn token
        }
        whenever(credentialsProvider.getLatestCredentials()).thenReturn(credentials)

        val actualClient = clientSupplier.invoke()

        assertThat(actualClient.reflectionToken).isSameAs("")
        assertThat(actualClient.reflectionDeviceType).isEqualTo(Client.DeviceType.PHONE)
        assertThat(actualClient.reflectionVersion).isSameAs(version)
        verify(credentialsProvider).getLatestCredentials()
        verify(context).resources
        verify(resources).getBoolean(R.bool.is_tablet)
        verify(uiModeManager, times(2)).currentModeType
        verify(credentials).token
        verifyNoMoreInteractions(resources, credentials)
    }
}
