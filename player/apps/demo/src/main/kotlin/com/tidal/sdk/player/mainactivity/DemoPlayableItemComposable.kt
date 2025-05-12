package com.tidal.sdk.player.mainactivity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tidal.sdk.player.common.model.MediaProduct

@Composable
@Suppress("LongMethod", "LongParameterList")
internal fun DemoPlayableItemComposable(
    item: DemoPlayableItem,
    isCurrent: Boolean,
    isNext: Boolean,
    dispatchLoad: (MediaProduct) -> Unit,
    calculateFollowingHardcodedMediaProduct: () -> MediaProduct?,
    dispatchSetNext: (MediaProduct?) -> Unit,
    dispatchPlay: () -> Unit,
    dispatchSkip: () -> Unit,
) {
    Column(
        modifier =
            Modifier.fillMaxWidth().padding(8.dp).clickable {
                dispatchLoad(item.createMediaProduct())
                dispatchSetNext(calculateFollowingHardcodedMediaProduct())
                dispatchPlay()
            }
    ) {
        Text(
            text = "${item.name} (${item.mediaProductId}, ${item.productType})",
            style =
                LocalTextStyle.current.copy(
                    color =
                        when {
                            isCurrent -> MaterialTheme.colorScheme.primary
                            isNext -> MaterialTheme.colorScheme.inversePrimary
                            else -> Color.Unspecified
                        }
                ),
        )
        Row {
            Column {
                TextButton(
                    enabled = !isCurrent,
                    onClick = { dispatchLoad(item.createMediaProduct()) },
                ) {
                    Text(text = if (isCurrent) "LOADED" else "LOAD")
                }
                TextButton(
                    enabled = !isNext,
                    onClick = { dispatchSetNext(item.createMediaProduct()) },
                ) {
                    Text(text = if (isNext) "SET AS NEXT" else "NEXT")
                }
            }
            Column {
                TextButton(
                    enabled = !isCurrent,
                    onClick = {
                        dispatchLoad(item.createMediaProduct())
                        dispatchPlay()
                    },
                ) {
                    Text(text = if (isCurrent) "LOADED" else "LOAD AND PLAY")
                }
                TextButton(
                    enabled = !isNext,
                    onClick = {
                        dispatchSetNext(item.createMediaProduct())
                        dispatchSkip()
                    },
                ) {
                    Text(text = if (isNext) "SET AS NEXT" else "NEXT AND SKIP TO")
                }
            }
        }
    }
}
