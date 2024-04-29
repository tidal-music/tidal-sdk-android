package com.tidal.sdk.player.playbackengine.offline

import java.io.IOException

/**
 * This is thrown when the offline expiration date has passed and we are not allowed to play from
 * offline.
 */
internal class OfflineExpiredException : IOException(
    "Offline play has expired. Please revalidate if possible.",
)
