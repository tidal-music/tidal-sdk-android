package com.tidal.sdk.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.auth.AuthProvider
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.eventproducer.model.EventsConfig
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Response

class MainActivity : ComponentActivity() {

    private lateinit var eventSender: EventSender
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventSender = EventSender.getInstance(
            TL_CONSUMER_URL,
            object : AuthProvider {
                override val token: String? = null
                override val clientId: String? = null
                override suspend fun handleAuthError(
                    response: Response,
                ): Boolean = false
            },
            EventsConfig(MAX_DISK_USAGE_BYTES, emptySet(), "1.0"),
            applicationContext,
            coroutineScope,
        )

        setContent {
            EventSenderDemoScreen {
                sendEvent()
            }
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
        private val TL_CONSUMER_URL =
            URL("https://event-collector.obelix-staging-use1.tidalhi.fi/")
    }
}
