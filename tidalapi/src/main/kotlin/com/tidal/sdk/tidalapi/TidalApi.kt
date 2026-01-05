package com.tidal.sdk.tidalapi

/**
 * Demo documentation block for [TidalApi]
 *
 * @param optInt The constructor [TidalApi] takes an optional [Int] just to tell you about in this
 *   comment. As this is used directly by the demo application, which is using Jetpack Compose, this
 *   is a data class,which makes it easily comparable and helps prevent unnecessary recomposition
 */
// Test change for tidalapi module
data class TidalApi(private val optInt: Int = 5) {

    /** This function returns the string "TidalApi! The string is: [optInt]" */
    fun helloTidalApi(): String {
        return "TidalApi! The int is: $optInt"
    }
}
