package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType

internal sealed class DownloadStatistics<T : DownloadStatistics.Payload> :
    StreamingMetrics<T>("download_statistics") {

    abstract override val payload: T

    sealed interface Payload : StreamingMetrics.Payload {

        val startTimestamp: Long
        val productType: ProductType
        val actualProductId: String
        val actualAssetPresentation: AssetPresentation
        val actualAudioMode: AudioMode
        val actualQuality: ProductQuality
        val endReason: EndReason
        val endTimestamp: Long
        val errorMessage: String?
        val errorCode: String?
    }
}
