package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.ProductType
import java.util.UUID

internal abstract class NotStartedPlaybackStatisticsMarshallingTest(
    productType: ProductType,
    endReason: EndReason,
) : PlaybackStatisticsMarshallingTest() {

    final override val ts = -1L
    final override val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    final override val user = User(8L, 54, "sessionId")
    final override val client = Client("token", Client.DeviceType.TV, "version")
    final override val payload = NotStartedPlaybackStatistics.Payload(
        "streamingSessionId",
        Long.MAX_VALUE,
        productType,
        Long.MAX_VALUE,
        "errorMessage",
        "errorCode",
        endReason,
    )
    override val playbackStatistics = NotStartedPlaybackStatistics(
        ts,
        UUID.fromString(uuidString),
        user,
        client,
        payload,
        null,
    )

    sealed class ProductTypeAudio(endReason: EndReason) :
        NotStartedPlaybackStatisticsMarshallingTest(ProductType.TRACK, endReason) {

        class EndReasonError : ProductTypeAudio(EndReason.ERROR)

        class EndReasonOther : ProductTypeAudio(EndReason.OTHER)
    }

    sealed class ProductTypeVideo(endReason: EndReason) :
        NotStartedPlaybackStatisticsMarshallingTest(ProductType.VIDEO, endReason) {

        class EndReasonError : ProductTypeVideo(EndReason.ERROR)

        class EndReasonOther : ProductTypeVideo(EndReason.OTHER)
    }

    sealed class ProductTypeBroadcast(endReason: EndReason) :
        NotStartedPlaybackStatisticsMarshallingTest(ProductType.BROADCAST, endReason) {

        class EndReasonError : ProductTypeBroadcast(EndReason.ERROR)

        class EndReasonOther : ProductTypeBroadcast(EndReason.OTHER)
    }
}
