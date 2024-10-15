package com.tidal.sdk.player.common.model

interface BaseMediaProduct {
    val productType: ProductType
    val productId: String
    val sourceType: String?
    val sourceId: String?
    val extras: Extras?
}
