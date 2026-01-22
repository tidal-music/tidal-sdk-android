package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetInstanceMemberProperty
import com.tidal.sdk.player.streamingapi.di.StreamingApiComponent

internal var StreamingApiModuleRoot.Companion.reflectionComponentFactoryF:
    () -> StreamingApiComponent.Factory
    get() = reflectionGetInstanceMemberProperty("componentFactoryF")!!
    set(value) = reflectionSetInstanceMemberProperty("componentFactoryF", value)
