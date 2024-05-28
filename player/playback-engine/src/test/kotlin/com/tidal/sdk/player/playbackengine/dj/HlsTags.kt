package com.tidal.sdk.player.playbackengine.dj

@Suppress("MaxLineLength")
internal object HlsTags {
    val TAGS_WITHOUT_DATE_RANGE = listOf(
        "#EXTM3U",
        "#EXT-X-VERSION:3",
        "#EXT-X-STREAM-INF:BANDWIDTH=98247,AVERAGE-BANDWIDTH=97169,CODECS=\"mp4a.40.5\"",
    )

    val TAGS_WITH_SINGLE_DATE_RANGE = listOf(
        "#EXTM3U",
        "#EXT-X-VERSION:7",
        "#EXT-X-DATERANGE:ID=\"p14\",CLASS=\"com.tidal.period.metadata\",START-DATE=\"2023-06-20T22:15:22.778794981Z\",X-COM-TIDAL-PRODUCT-ID=\"78067540\",END-ON-NEXT=YES,X-COM-TIDAL-STATUS=\"PLAYING\"", // ktlint-disable max-line-length
        "#EXTINF:1.997, https://url.com",
        "#EXT-X-ENDLIST",
    )

    val TAGS_WITH_MULTIPLE_DATE_RANGE = listOf(
        "#EXTM3U",
        "#EXT-X-VERSION:7",
        "#EXT-X-DATERANGE:ID=\"p14\",CLASS=\"com.tidal.period.metadata\",START-DATE=\"2023-06-20T22:15:22.778794981Z\",X-COM-TIDAL-PRODUCT-ID=\"78067540\",END-ON-NEXT=YES,X-COM-TIDAL-STATUS=\"PLAYING\"", // ktlint-disable max-line-length
        "#EXTINF:1.997, https://url.com",
        "#EXT-X-DATERANGE:ID=\"p15\",CLASS=\"com.tidal.period.metadata\",START-DATE=\"2023-06-20T22:18:22.778794981Z\",X-COM-TIDAL-PRODUCT-ID=\"78067541\",END-ON-NEXT=YES,X-COM-TIDAL-STATUS=\"PAUSED\"", // ktlint-disable max-line-length
        "#EXTINF:1.997, https://url.com",
        "#EXT-X-ENDLIST",
    )
}
