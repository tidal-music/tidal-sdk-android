package com.tidal.sdk.player.playbackengine.util

import android.view.SurfaceHolder
import com.tidal.sdk.player.reflectionGetInstanceMemberProperty

internal val SynchronousSurfaceHolder.reflectionCallbackMappings:
    MutableMap<SurfaceHolder.Callback, SynchronousSurfaceHolderCallback>
    get() = reflectionGetInstanceMemberProperty("callbackMap")!!
