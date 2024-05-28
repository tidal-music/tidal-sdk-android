package com.tidal.sdk.player.mainactivity

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
internal fun PlayerNotInitializedScreen(
    paddingValues: PaddingValues = PaddingValues(),
    dispatchCreatePlayerWithExternalCache: (Context, Boolean) -> Unit,
    dispatchCreatePlayerWithInternalCache: (Context, Boolean) -> Unit,
) {
    var provideExternalCache by rememberSaveable { mutableStateOf(false) }
    var startInOfflineMode by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.padding(paddingValues)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Provide external cache?",
                modifier = Modifier
                    .padding(PaddingValues(end = 8F.dp))
                    .weight(1F, fill = false),
            )
            Switch(checked = provideExternalCache, onCheckedChange = { provideExternalCache = it })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Start in offline mode?",
                modifier = Modifier
                    .padding(PaddingValues(end = 8F.dp))
                    .weight(1F, fill = false),
            )
            Switch(checked = startInOfflineMode, onCheckedChange = { startInOfflineMode = it })
        }
        val context = LocalContext.current.applicationContext
        Button(
            onClick = {
                if (provideExternalCache) {
                    dispatchCreatePlayerWithExternalCache(context, startInOfflineMode)
                } else {
                    dispatchCreatePlayerWithInternalCache(context, startInOfflineMode)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "CREATE PLAYER")
        }
    }
}
