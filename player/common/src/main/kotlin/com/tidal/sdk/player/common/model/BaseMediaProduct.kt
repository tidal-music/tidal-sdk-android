package com.tidal.sdk.player.common.model

interface BaseMediaProduct {
    val productType: ProductType
    val productId: String
    val sourceType: String?
    val sourceId: String?
    val extras: Extras?

    /** Grants playback of an upload shared by link to a recipient who has not saved the share. */
    val shareCode: String?
        get() = null
}
