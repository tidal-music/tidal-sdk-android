package com.tidal.sdk.player.playbackengine.dj

internal class HlsTagsParser {

    fun parse(tags: List<String>) =
        tags.filter { it.startsWith("#EXT-X-DATERANGE") }.map { parseDateRange(it) }

    private fun parseDateRange(tag: String): DateRange {
        var id = ""
        var clazz = ""
        var startDate = ""
        var productId = ""
        var status = DjSessionStatus.UNAVAILABLE
        var endOnNext = ""

        val dateRangeAttrs = tag.substring(tag.indexOf(':') + 1)
        dateRangeAttrs.split(',').map {
            val dateRangeAttr = it.split('=')
            val key = dateRangeAttr[0]
            val value = dateRangeAttr[1].removeSurrounding("\"")
            when (key) {
                "ID" -> id = value
                "CLASS" -> clazz = value
                "START-DATE" -> startDate = value
                "X-COM-TIDAL-PRODUCT-ID" -> productId = value
                "X-COM-TIDAL-STATUS" ->
                    DjSessionStatus.values()
                        .firstOrNull { it.name.contentEquals(value) }
                        ?.let { status = it } ?: Unit

                "END-ON-NEXT" -> endOnNext = value
            }
        }

        return DateRange(id, clazz, startDate, productId, status, endOnNext)
    }
}
