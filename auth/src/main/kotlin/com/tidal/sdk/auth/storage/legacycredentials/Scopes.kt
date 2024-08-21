package com.tidal.sdk.auth.storage.legacycredentials

import kotlinx.serialization.Serializable

/**
 * Represents a set of scopes. Scopes are used to define the capabilities of a TIDAL client.
 * @param scopes The set of scopes.
 */
@Deprecated("Use [String] instead.")
@Serializable
data class Scopes(val scopes: Set<String>) {

    /**
     * Returns a string representation of the scopes that is readable by the TIDAL API backend.
     * @return The string representation of the scopes.
     */
    override fun toString(): String = scopes.joinToString(" ")

    companion object {

        /**
         * Creates a Scopes object from a string representation of the scopes.
         * @param joinedString The string representation of the scopes.
         * @return The Scopes object.
         */
        fun fromString(joinedString: String): Scopes = Scopes(joinedString.split(" ").toSet())
    }
}
