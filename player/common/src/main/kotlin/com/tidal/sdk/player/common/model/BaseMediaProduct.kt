package com.tidal.sdk.player.common.model

interface BaseMediaProduct {

    val productType: ProductType
    val productId: String
    val sourceType: String?
    val sourceId: String?
    val extras: Extras?

    sealed interface Extras {
        data class Primitive(val content: String) : Extras

        data class Collection(val content: Map<String, Extras>) : Extras
    }
}
