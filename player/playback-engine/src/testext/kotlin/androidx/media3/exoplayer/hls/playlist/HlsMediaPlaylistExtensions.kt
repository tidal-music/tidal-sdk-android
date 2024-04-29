package androidx.media3.exoplayer.hls.playlist

import com.tidal.sdk.player.reflectionSetInstanceFinalField

internal fun HlsMediaPlaylist.reflectionSetTags(value: List<String>) =
    reflectionSetInstanceFinalField("tags", value)
