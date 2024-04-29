package com.tidal.sdk.auth.model

import kotlinx.serialization.Serializable

/**
 * Represents a set of scopes. Scopes are used to define the capabilities of a TIDAL client.
 * @param scopes The set of scopes.
 */
@Serializable
data class Scopes(val scopes: Set<String>) {

    init {
        val regex = SINGLE_VALID_SCOPE_REGEX.toRegex()
        if (scopes.isNotEmpty() && scopes.any { regex.matches(it).not() }) {
            error(ILLEGAL_SCOPES_MESSAGE)
        }
    }

    /**
     * Returns a string representation of the scopes that is readable by the TIDAL API backend.
     * @return The string representation of the scopes.
     */
    override fun toString(): String {
        return scopes.joinToString(" ")
    }

    companion object {

        const val VALID_SCOPES_STRING_REGEX = "([rw]_[a-z]+\\s)*[rw]_[a-z]+"
        private const val SINGLE_VALID_SCOPE_REGEX = "[rw]_[a-z]+"
        private const val ILLEGAL_SCOPES_MESSAGE = "Submitted Scopes are invalid!"

        /**
         * Creates a Scopes object from a string representation of the scopes.
         * @param joinedString The string representation of the scopes.
         * @return The Scopes object.
         */
        fun fromString(joinedString: String): Scopes {
            return when {
                joinedString.isBlank() -> {
                    Scopes(setOf())
                }

                VALID_SCOPES_STRING_REGEX.toRegex().matches(joinedString) -> {
                    Scopes(joinedString.split(" ").toSet())
                }

                else -> {
                    error(ILLEGAL_SCOPES_MESSAGE)
                }
            }
        }
    }
}
