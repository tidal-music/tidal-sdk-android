package com.tidal.sdk.eventproducer.utils

import android.os.Build
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.model.ConsentCategory
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

internal const val CONSENT_CATEGORY_KEY = "consent-category"
internal const val OS_NAME_KEY = "os-name"
internal const val OS_VERSION_KEY = "os-version"
internal const val APP_VERSION_KEY = "app-version"
internal const val REQUESTED_SENT_TIMESTAMP_KEY = "requested-sent-timestamp"
internal const val AUTHORIZATION_KEY = "authorization"
internal const val CLIENT_ID_KEY = "client-id"
internal const val DEVICE_MODEL_KEY = "device-model"
internal const val DEVICE_VENDOR_KEY = "device-vendor"

internal class HeadersUtils @Inject constructor(
    private val appVersion: String,
    private val credentialsProvider: CredentialsProvider,
) {
    fun getEventHeaders(
        defaultHeaders: Map<String, String>,
        suppliedHeaders: Map<String, String>,
    ): Map<String, String> = defaultHeaders + suppliedHeaders

    fun getDefaultHeaders(
        consentCategory: ConsentCategory,
        isMonitoringEvent: Boolean,
    ): Map<String, String> {
        val deviceModel = Build.MODEL
        val deviceVendor = Build.MANUFACTURER
        val sentTimestamp = System.currentTimeMillis().toString()
        val osName = "Android"
        val osVersion = Build.VERSION.SDK_INT.toString()
        val headers = mutableMapOf<String, String>()
        headers[APP_VERSION_KEY] = appVersion
        headers[OS_VERSION_KEY] = osVersion
        headers[OS_NAME_KEY] = osName
        headers[REQUESTED_SENT_TIMESTAMP_KEY] = sentTimestamp
        headers[DEVICE_VENDOR_KEY] = deviceVendor
        headers[DEVICE_MODEL_KEY] = deviceModel
        headers[CONSENT_CATEGORY_KEY] = consentCategory.toString()
        val credentials = runBlocking { credentialsProvider.getCredentials().successData }
        val token = credentials?.token
        if (!isMonitoringEvent && !token.isNullOrEmpty()) {
            headers[AUTHORIZATION_KEY] = token
        }
        val clientId = credentials?.clientId
        if (!clientId.isNullOrEmpty()) headers[CLIENT_ID_KEY] = clientId
        return headers
    }
}
