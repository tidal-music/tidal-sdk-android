package com.tidal.sdk.player.playbackengine.dj

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class HlsTagsParserTest {

    private val dateRange1 =
        DateRange(
            "p14",
            "com.tidal.period.metadata",
            "2023-06-20T22:15:22.778794981Z",
            "78067540",
            DjSessionStatus.PLAYING,
            "YES",
        )

    private val dateRange2 =
        DateRange(
            "p15",
            "com.tidal.period.metadata",
            "2023-06-20T22:18:22.778794981Z",
            "78067541",
            DjSessionStatus.PAUSED,
            "YES",
        )

    private val hlsTagsParser = HlsTagsParser()

    @Test
    fun `parse tags without any date ranges returns empty list`() {
        val dateRanges = hlsTagsParser.parse(HlsTags.TAGS_WITHOUT_DATE_RANGE)

        assertThat(dateRanges).isEmpty()
    }

    @Test
    fun `parse tags with single date range returns list of a single date range`() {
        val expectedDateRanges = listOf(dateRange1)

        val actualDateRanges = hlsTagsParser.parse(HlsTags.TAGS_WITH_SINGLE_DATE_RANGE)

        assertThat(actualDateRanges).isEqualTo(expectedDateRanges)
    }

    @Test
    fun `parse tags with multiple date ranges returns list of multiple date ranges`() {
        val expectedDateRanges = listOf(dateRange1, dateRange2)

        val actualDateRanges = hlsTagsParser.parse(HlsTags.TAGS_WITH_MULTIPLE_DATE_RANGE)

        assertThat(actualDateRanges).isEqualTo(expectedDateRanges)
    }
}
