package com.tidal.sdk.template

/**
 * Demo documentation block for [Template]
 *
 * @param optInt The constructor [Template] takes an optional [Int] just to tell you about in this
 *   comment. As this is used directly by the demo application, which is using Jetpack Compose, this
 *   is a data class,which makes it easily comparable and helps prevent unnecessary recomposition
 */
data class Template(private val optInt: Int = 5) {

    /** This function returns the string "Template! The string is: [optInt]" */
    fun helloTemplate(): String {
        return "Template! The int is: $optInt"
    }
}
