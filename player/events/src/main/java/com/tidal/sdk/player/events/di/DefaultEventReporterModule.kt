package com.tidal.sdk.player.events.di

import com.google.gson.Gson
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.events.DefaultEventReporter
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.converter.EventFactory
import com.tidal.sdk.player.events.model.Event
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.coroutines.CoroutineScope

@Module
internal object DefaultEventReporterModule {

    @Provides
    @Reusable
    fun eventReporter(
        eventFactories:
            Map<
                Class<out Event.Payload>,
                @JvmSuppressWildcards
                EventFactory<out Event.Payload>,
            >, // ktlint-disable max-line-length parameter-wrapping
        eventSender: EventSender,
        gson: Gson,
        coroutineScope: CoroutineScope,
    ): EventReporter = DefaultEventReporter(eventFactories, eventSender, gson, coroutineScope)
}
