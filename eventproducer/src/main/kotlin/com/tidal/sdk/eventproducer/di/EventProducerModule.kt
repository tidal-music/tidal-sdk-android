package com.tidal.sdk.eventproducer.di

import com.tidal.sdk.eventproducer.DefaultEventSender
import com.tidal.sdk.eventproducer.EventSender
import dagger.Binds
import dagger.Module

@Module
internal interface EventProducerModule {

    @Binds
    fun provideEventSender(defaultEventSender: DefaultEventSender): EventSender
}
