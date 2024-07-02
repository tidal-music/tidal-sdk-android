package com.tidal.sdk.demo.ui.catalogue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tidal.sdk.demo.MainActivity
import com.tidal.sdk.demo.ui.findActivity
import kotlinx.coroutines.launch

@Composable
fun CatalogueTestScreen() {
    val activity = LocalContext.current.findActivity() as MainActivity
    val composableScope = rememberCoroutineScope()
    val catalogueTestViewModel: CatalogueTestViewModel =
        viewModel { CatalogueTestViewModel(activity.catalogue) }
    val state =
        catalogueTestViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Column {
            Text(
                text = "Pressing the button will trigger a " +
                    "hardcoded catalogue call and show the result",
                color = Color.White,
            )

            Button(
                onClick = {
                    composableScope.launch {
                        catalogueTestViewModel.loadData()
                    }
                },
            ) {
                Text("Try catalogue")
            }
            Text(text = state.value.data ?: "", color = Color.White)
        }
    }
}
