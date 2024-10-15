package com.tidal.sdk.player.common.model

typealias Extras = Map<String, Extra?>

sealed interface Extra {
    data class Bool(val value: Boolean) : Extra

    data class Number(val value: kotlin.Number) : Extra

    data class Text(val value: String) : Extra

    data class CollectionList(val value: List<Extra>?) : Extra

    data class CollectionMap(val value: Extras?) : Extra
}
