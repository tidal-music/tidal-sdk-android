package com.tidal.sdk.player.playbackengine.player

import androidx.media3.datasource.cache.Cache

/**
 * A wrapper around [Cache] so we can tell if it is provided from the outside or created from
 * within. This is important because we should not e.g. release something that is provided from
 * the outside, because it might be in use in other places and will thus create problems.
 */
@Suppress("UnsafeOptInUsageError")
internal sealed class PlayerCache private constructor(val cache: Cache) {

    class Provided(cache: Cache) : PlayerCache(cache)
    class Internal(cache: Cache) : PlayerCache(cache)
}
