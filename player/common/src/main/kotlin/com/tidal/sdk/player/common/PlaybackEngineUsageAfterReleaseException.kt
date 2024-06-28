package com.tidal.sdk.player.common

class PlaybackEngineUsageAfterReleaseException :
    IllegalStateException("Attempted to use a released PlaybackEngine")
