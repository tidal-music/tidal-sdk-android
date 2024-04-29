package com.tidal.sdk.player.mainactivity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tidal.sdk.player.common.model.AudioQuality

@Composable
@Suppress("LongMethod")
internal fun <T> Selector(
    title: String,
    selectedValue: T,
    possibleValues: Array<T>,
    onSelectionUpdated: (T) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { expanded = true }
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(PaddingValues(end = 8F.dp))
                .weight(1F, fill = false),
        )
        Text(text = selectedValue.toString())
    }
    if (expanded) {
        Dialog(onDismissRequest = { expanded = false }) {
            Surface(shape = RoundedCornerShape(12F.dp)) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16F.dp),
                    )
                    val selectedIndex = possibleValues.indexOf(selectedValue)
                    val listState = rememberLazyListState()
                    LaunchedEffect("ScrollToSelected") {
                        listState.scrollToItem(index = selectedIndex)
                    }
                    LazyColumn(state = listState) {
                        itemsIndexed(possibleValues) { index, item ->
                            val contentColor = when (index) {
                                selectedIndex -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable {
                                        expanded = false
                                        onSelectionUpdated(item)
                                    }
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            ) {
                                CompositionLocalProvider(LocalContentColor provides contentColor) {
                                    Text(
                                        text = item.toString(),
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                }
                            }
                            if (index < AudioQuality.values().lastIndex) {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
