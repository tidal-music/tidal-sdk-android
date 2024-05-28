package com.tidal.sdk.player.playbackengine.mediasource.loadable

import com.tidal.sdk.player.common.ForwardingMediaProduct

internal sealed class PlaybackInfoFetchException private constructor(
    val requestedMediaProduct: ForwardingMediaProduct<*>,
    cause: Throwable?,
    message: String,
) : Exception(message, cause) {

    class Cancellation(requestedMediaProduct: ForwardingMediaProduct<*>) :
        PlaybackInfoFetchException(requestedMediaProduct, null, "PlaybackInfo fetch cancelled")

    class Error(requestedMediaProduct: ForwardingMediaProduct<*>, cause: Throwable) :
        PlaybackInfoFetchException(requestedMediaProduct, cause, "PlaybackInfo fetch failed")
}
