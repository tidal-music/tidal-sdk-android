package com.tidal.sdk.eventproducer.repository

import com.squareup.moshi.Moshi
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.eventproducer.model.Event
import com.tidal.sdk.eventproducer.monitoring.MonitoringInfo
import com.tidal.sdk.eventproducer.utils.DatabaseSizeChecker
import com.tidal.sdk.eventproducer.utils.HeadersUtils
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking

@Singleton
internal class RepositoryHelper @Inject constructor(
    private val credentialsProvider: CredentialsProvider,
    private val databaseSizeChecker: DatabaseSizeChecker,
    private val moshi: Moshi,
    private val headersUtils: HeadersUtils,
) {

    fun isDatabaseLimitReached() = databaseSizeChecker.isDatabaseLimitReached()

    fun isUserLoggedIn(): Boolean {
        val credentials = runBlocking { credentialsProvider.getCredentials().successData }
        return credentials?.level == Credentials.Level.USER
    }

    fun getMonitoringEvent(monitoringInfo: MonitoringInfo): Event {
        val monitoringEventPayload = getMonitoringEventPayload(monitoringInfo)
        val headers = headersUtils.getDefaultHeaders(ConsentCategory.NECESSARY, true)
        return Event(
            UUID.randomUUID().toString(),
            MONITORING_EVENT_NAME,
            headers,
            monitoringEventPayload,
        )
    }

    private fun getMonitoringEventPayload(monitoringInfo: MonitoringInfo): String {
        val adapter = moshi.adapter(MonitoringInfo::class.java)
        return adapter.toJson(monitoringInfo)
    }

    companion object {

        const val MONITORING_EVENT_NAME = "tep-tl-monitoring"
    }
}
