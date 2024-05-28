package com.tidal.sdk.player.events

import com.google.gson.Gson
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.player.events.converter.EventFactory
import com.tidal.sdk.player.events.model.AudioPlaybackSession
import com.tidal.sdk.player.events.model.Event
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class DefaultEventReporterTest {

    private val eventFactories =
        mock<Map<Class<out Event.Payload>, EventFactory<out Event.Payload>>>()
    private val eventSender = mock<EventSender>()
    private val gson = mock<Gson>()
    private val coroutineScope = TestScope(StandardTestDispatcher(TestCoroutineScheduler()))
    private val defaultEventReporter =
        DefaultEventReporter(eventFactories, eventSender, gson, coroutineScope)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(eventFactories, eventSender, gson)

    @Test
    fun report() = runTest {
        val name = "eventName"
        val consentCategory = mock<ConsentCategory>()
        val event = mock<AudioPlaybackSession> {
            on { it.name } doReturn name
            on { it.consentCategory } doReturn consentCategory
        }
        val jsonString = "event json"
        whenever(gson.toJson(event)) doReturn jsonString
        val payload = mock<AudioPlaybackSession.Payload>()
        val eventFactory = mock<EventFactory<Event.Payload>> {
            on { runBlocking { invoke(payload) } } doReturn event
        }
        whenever(eventFactories[payload::class.java]) doReturn eventFactory

        defaultEventReporter.report(payload)

        coroutineScope.testScheduler.advanceUntilIdle()

        verify(eventFactories)[payload::class.java]
        verify(eventFactory)(payload)
        verify(event).name
        verify(event).consentCategory
        verify(gson).toJson(event)
        verify(eventSender).sendEvent(name, consentCategory, jsonString, emptyMap())
        verifyNoMoreInteractions(consentCategory, event, payload, eventFactory)
    }
}
