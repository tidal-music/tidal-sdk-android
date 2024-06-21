package com.tidal.sdk.catalogue

/**
 * Demo documentation block for [Catalogue]
 * @param optInt The constructor [Catalogue] takes an optional [Int]
 * just to tell you about in this comment.
 * As this is used directly by the demo application, which is using
 * Jetpack Compose, this is a data class,which makes it easily comparable
 * and helps prevent unnecessary recomposition
 */
data class Catalogue(private val optInt: Int = 5) {
    /**
     * This function returns the string "Catalogue! The string is: [optInt]"
     */
    fun helloCatalogue(): String {
        return "Catalogue! The int is: $optInt"
    }
}
