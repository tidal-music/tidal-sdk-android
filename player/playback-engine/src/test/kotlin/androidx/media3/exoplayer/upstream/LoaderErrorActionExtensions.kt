package androidx.media3.exoplayer.upstream

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty

internal val Loader.LoadErrorAction.reflectionType: Int
    get() = reflectionGetInstanceMemberProperty("type")!!
