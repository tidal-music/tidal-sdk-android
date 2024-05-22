package com.tidal.sdk.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.eventproducer.EventProducer
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.eventproducer.model.EventsConfig
import java.net.URI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Instant

class MainActivity : ComponentActivity() {

    private lateinit var eventSender: EventSender
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val eventProducer = EventProducer.getInstance(
            getCredentialsProvider(),
            EventsConfig(MAX_DISK_USAGE_BYTES, emptySet(), "1.0"),
            applicationContext,
            coroutineScope,
            TL_CONSUMER_URI,
        )
        eventSender = eventProducer.eventSender

        setContent {
            EventSenderDemoScreen {
                sendEvent()
            }
        }
    }

    private fun getCredentialsProvider(): CredentialsProvider {
        val credentials = Credentials(
            clientId = "123",
            requestedScopes = emptySet(),
            clientUniqueKey = "clientUniqueKey",
            grantedScopes = emptySet(),
            userId = "123",
            expires = Instant.DISTANT_FUTURE,
            token = null,
        )
        return object : CredentialsProvider {
            override val bus: Flow<TidalMessage>
                get() = flowOf()

            override suspend fun getCredentials(
                apiErrorSubStatus: String?,
            ): AuthResult<Credentials> {
                return AuthResult.Success(credentials)
            }

            override fun isUserLoggedIn() = true
        }
    }

    private fun sendEvent() {
        eventSender.sendEvent(
            "event1",
            ConsentCategory.NECESSARY,
            "{'group':'onboarding','name':'test-0000001'}",
            emptyMap(),
        )
    }

    companion object {
        private const val MAX_DISK_USAGE_BYTES = 1000000
        private val TL_CONSUMER_URI =
            URI("https://event-collector.obelix-staging-use1.tidalhi.fi/")
    }
}
