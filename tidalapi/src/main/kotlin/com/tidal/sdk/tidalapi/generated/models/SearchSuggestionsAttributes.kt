/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.tidal.sdk.tidalapi.generated.models

import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsHistory
import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsSuggestions

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Transient

/**
 * 
 *
 * @param trackingId Unique tracking id
 * @param history Suggestions from search history
 * @param suggestions Suggested search queries
 */

@Serializable

data class SearchSuggestionsAttributes (

    /* Unique tracking id */
    
    @SerialName(value = "trackingId")
    val trackingId: kotlin.String,
    /* Suggestions from search history */
    
    @SerialName(value = "history")
    val history: kotlin.collections.List<SearchSuggestionsHistory>? = null,
    /* Suggested search queries */
    
    @SerialName(value = "suggestions")
    val suggestions: kotlin.collections.List<SearchSuggestionsSuggestions>? = null
) {


}

