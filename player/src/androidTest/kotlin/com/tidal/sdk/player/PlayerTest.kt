package com.tidal.sdk.player

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.player.playbackengine.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

class PlayerTest {

    private val credentialsProvider = object : CredentialsProvider {
        override val bus: Flow<TidalMessage>
            get() = throw IllegalAccessException("Not supported")

        override suspend fun getCredentials(apiErrorSubStatus: String?) =
            throw IllegalAccessException("Not supported")

        override fun isUserLoggedIn() = false
    }

    private val eventSender = object : EventSender {
        override fun sendEvent(
            eventName: String,
            consentCategory: ConsentCategory,
            payload: String,
            headers: Map<String, String>,
        ) = throw IllegalAccessException("Not supported")

        override fun setBlockedConsentCategories(blockedConsentCategories: Set<ConsentCategory>) =
            throw IllegalAccessException("Not supported")
    }

    private val player = Player(
        InstrumentationRegistry.getInstrumentation()
            .targetContext
            .applicationContext as Application,
        credentialsProvider,
        eventSender,
    )

    @After
    fun afterEach() = runBlocking {
        val job = launch { player.playbackEngine.events.first { it is Event.Release } }
        player.release()
        job.join()
    }

    @Test
    fun assetPositionIsAccessibleAndDefaultsToZero() {
        assertThat(player.playbackEngine.assetPosition).isEqualTo(0.0f)
    }
}
