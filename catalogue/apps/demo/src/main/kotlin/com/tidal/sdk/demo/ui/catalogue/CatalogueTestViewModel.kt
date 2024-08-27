package com.tidal.sdk.demo.ui.catalogue

import androidx.lifecycle.ViewModel
import com.tidal.sdk.catalogue.generated.Catalogue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatalogueTestViewModel(private val catalogue: Catalogue) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    suspend fun loadData() {
        val result = catalogue.createAlbumJSONAPI().getAlbumItems(
            "328638757",
            "DE"
        ).body().toString()
        _uiState.emit(UIState(result))
    }

    data class UIState(val data: String? = null)
}
