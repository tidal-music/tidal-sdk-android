package com.tidal.sdk.player.events

import com.tidal.sdk.player.events.di.DefaultEventReporterComponent
import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetInstanceMemberProperty

internal var EventReporterModuleRoot.Companion.reflectionComponentFactoryF:
    () -> DefaultEventReporterComponent.Factory
    get() = reflectionGetInstanceMemberProperty("componentFactoryF")!!
    set(value) = reflectionSetInstanceMemberProperty("componentFactoryF", value)
