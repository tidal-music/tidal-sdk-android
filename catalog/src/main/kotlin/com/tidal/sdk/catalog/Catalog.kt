package com.tidal.sdk.catalog

/**
 * Demo documentation block for [Catalog]
 * @param optInt The constructor [Catalog] takes an optional [Int]
 * just to tell you about in this comment.
 * As this is used directly by the demo application, which is using
 * Jetpack Compose, this is a data class,which makes it easily comparable
 * and helps prevent unnecessary recomposition
 */
data class Catalog(private val optInt: Int = 5) {
    /**
     * This function returns the string "Catalog! The string is: [optInt]"
     */
    fun helloCatalog(): String {
        return "Catalog! The int is: $optInt"
    }
}
