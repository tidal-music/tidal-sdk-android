package com.tidal.sdk.player.common.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.AssetPresentation.FULL
import com.tidal.sdk.player.common.model.AssetPresentation.PREVIEW

/**
 * Asset presentation of a given product.
 *
 * [FULL] is the whole track or video, which requires a subscription.
 * [PREVIEW] is a 30 second preview of the track or video, which does not require a subscription.
 */
@Keep
enum class AssetPresentation {
    FULL,
    PREVIEW,
}
