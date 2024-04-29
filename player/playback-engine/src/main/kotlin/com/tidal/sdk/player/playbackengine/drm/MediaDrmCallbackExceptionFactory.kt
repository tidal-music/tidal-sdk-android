package com.tidal.sdk.player.playbackengine.drm

import android.net.Uri
import androidx.media3.datasource.DataSpec
import androidx.media3.exoplayer.drm.MediaDrmCallbackException

internal class MediaDrmCallbackExceptionFactory {

    private val dataSpec = DataSpec.Builder().setUri(Uri.EMPTY).build()

    fun create(throwable: Throwable) = MediaDrmCallbackException(
        dataSpec,
        dataSpec.uri,
        emptyMap(),
        0,
        throwable,
    )
}
