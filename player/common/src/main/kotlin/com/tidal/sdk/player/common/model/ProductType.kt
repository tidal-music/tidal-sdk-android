package com.tidal.sdk.player.common.model

import androidx.annotation.Keep

/** A product type that determines if this product is a track, a video or a broadcast. */
@Keep
enum class ProductType {
    /** Used to mark this product as a track. */
    TRACK,

    /** Used to mark this product as a video. */
    VIDEO,

    /** Used to mark this product as a broadcast. */
    BROADCAST,

    /** Used to mark this product as UC. */
    UC,
}
