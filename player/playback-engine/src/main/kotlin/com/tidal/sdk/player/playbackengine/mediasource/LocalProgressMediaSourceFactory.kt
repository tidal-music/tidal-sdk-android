package com.tidal.sdk.player.playbackengine.mediasource

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource

class LocalProgressMediaSourceFactory(
    private val context: Context
) {

    fun create(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSource.Factory(context)
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))
    }
}
