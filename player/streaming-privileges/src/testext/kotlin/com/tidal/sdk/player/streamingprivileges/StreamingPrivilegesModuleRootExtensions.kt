package com.tidal.sdk.player.streamingprivileges

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetInstanceMemberProperty
import com.tidal.sdk.player.streamingprivileges.di.StreamingPrivilegesComponent

internal var StreamingPrivilegesModuleRoot.Companion.reflectionComponentFactoryF:
    () -> StreamingPrivilegesComponent.Factory
    get() = reflectionGetInstanceMemberProperty("componentFactoryF")!!
    set(value) = reflectionSetInstanceMemberProperty("componentFactoryF", value)
