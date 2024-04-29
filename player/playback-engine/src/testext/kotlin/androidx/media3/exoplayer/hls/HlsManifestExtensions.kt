package androidx.media3.exoplayer.hls

import androidx.media3.exoplayer.hls.playlist.HlsMediaPlaylist
import com.tidal.sdk.player.reflectionSetInstanceFinalField

internal fun HlsManifest.reflectionSetMediaPlaylist(value: HlsMediaPlaylist) =
    reflectionSetInstanceFinalField("mediaPlaylist", value)
