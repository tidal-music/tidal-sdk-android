package com.tidal.sdk.player.playbackengine.dj

import android.os.Handler
import assertk.assertThat
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class DjSessionManagerTest {

    private val hlsTagsParser = mock<HlsTagsParser>()
    private val dateParser = mock<DateParser>()
    private val handler = mock<Handler>()
    private val djSessionManager = DjSessionManager(hlsTagsParser, dateParser, handler)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(hlsTagsParser, handler)

    @Test
    fun `cleanUp removes listener and cleans up even if no dateRange present`() {
        djSessionManager.cleanUp()

        assertThat(djSessionManager.listener).isNull()
        verify(handler).removeCallbacksAndMessages(null)
        assertThat(djSessionManager.reflectionCurrentDateRange).isNull()
    }

    @Test
    fun `cleanUp removes listener and cleans up with currentDateRange set`() {
        val dateRange = mock<DateRange>()
        djSessionManager.reflectionCurrentDateRange = dateRange

        djSessionManager.cleanUp()

        assertThat(djSessionManager.listener).isNull()
        verify(handler).removeCallbacksAndMessages(null)
        assertThat(djSessionManager.reflectionCurrentDateRange).isNull()
    }

    @Test
    fun `checkForUpdates when tags are empty`() {
        val tags = HlsTags.TAGS_WITHOUT_DATE_RANGE
        djSessionManager.checkForUpdates(tags, 123L)

        verify(hlsTagsParser).parse(tags)
        assertThat(djSessionManager.reflectionCurrentDateRange).isNull()
    }

    @Test
    fun `checkForUpdates when one tag and dateRange is in the future`() {
        val tags = HlsTags.TAGS_WITH_SINGLE_DATE_RANGE
        djSessionManager.checkForUpdates(tags, 123L)

        verify(hlsTagsParser).parse(tags)
        assertThat(djSessionManager.reflectionCurrentDateRange).isNull()
    }

    @Test
    fun `checkForUpdates when one tag and dateRange is in the past`() {
        val productId = "123abc"
        val status = DjSessionStatus.PLAYING
        val dateRange =
            mock<DateRange> {
                on { it.productId } doReturn productId
                on { it.startDate } doReturn "123"
                on { it.status } doReturn status
            }
        val dateRanges =
            mock<List<DateRange>> {
                on { it[0] } doReturn dateRange
                on { it.size } doReturn 1
            }
        whenever(dateParser.parseXsDateTime("123")).thenReturn(123)
        val tags = HlsTags.TAGS_WITH_SINGLE_DATE_RANGE
        whenever(hlsTagsParser.parse(tags)).thenReturn(dateRanges)
        val listener = mock<DjSessionManager.Listener>()
        djSessionManager.listener = listener

        djSessionManager.checkForUpdates(tags, 123L)

        verify(hlsTagsParser).parse(tags)
        verify(listener).onDjSessionUpdated(productId, status)
        assertThat(djSessionManager.reflectionCurrentDateRange).isSameAs(dateRanges[0])
    }

    @Test
    fun `checkForUpdates when multiple tags and dateRange is in the future`() {
        val productId = "123abc"
        val status = DjSessionStatus.PLAYING
        val dateRange =
            mock<DateRange> {
                on { it.productId } doReturn productId
                on { it.startDate } doReturn "123"
                on { it.status } doReturn status
            }
        val productIdNext = "456def"
        val statusNext = DjSessionStatus.PAUSED
        val dateRangeNext =
            mock<DateRange> {
                on { it.productId } doReturn productIdNext
                on { it.startDate } doReturn "456"
                on { it.status } doReturn statusNext
            }
        val dateRanges =
            mock<List<DateRange>> {
                on { it[0] } doReturn dateRange
                on { it[1] } doReturn dateRangeNext
                on { it.size } doReturn 2
            }
        whenever(dateParser.parseXsDateTime("456")).thenReturn(456)
        val tags = HlsTags.TAGS_WITH_MULTIPLE_DATE_RANGE
        whenever(hlsTagsParser.parse(tags)).thenReturn(dateRanges)
        val listener = mock<DjSessionManager.Listener>()
        djSessionManager.listener = listener

        djSessionManager.checkForUpdates(tags, 123L)

        verify(hlsTagsParser).parse(tags)
        verify(listener).onDjSessionUpdated(productId, status)
        assertThat(djSessionManager.reflectionCurrentDateRange).isSameAs(dateRanges[0])
        verify(handler).removeCallbacksAndMessages(null)
        verify(handler).postDelayed(any(), eq(333))
    }

    @Test
    fun `checkForUpdates when multiple tags and dateRange is in the past`() {
        val productId = "123abc"
        val status = DjSessionStatus.PLAYING
        val dateRange =
            mock<DateRange> {
                on { it.productId } doReturn productId
                on { it.startDate } doReturn "123"
                on { it.status } doReturn status
            }
        val productIdNext = "456def"
        val statusNext = DjSessionStatus.PAUSED
        val dateRangeNext =
            mock<DateRange> {
                on { it.productId } doReturn productIdNext
                on { it.startDate } doReturn "41"
                on { it.status } doReturn statusNext
            }
        val dateRanges =
            mock<List<DateRange>> {
                on { it[0] } doReturn dateRange
                on { it[1] } doReturn dateRangeNext
                on { it.size } doReturn 2
            }
        whenever(dateParser.parseXsDateTime("41")).thenReturn(41)
        val tags = HlsTags.TAGS_WITH_MULTIPLE_DATE_RANGE
        whenever(hlsTagsParser.parse(tags)).thenReturn(dateRanges)
        val listener = mock<DjSessionManager.Listener>()
        djSessionManager.listener = listener

        djSessionManager.checkForUpdates(tags, 123L)

        verify(hlsTagsParser).parse(tags)
        verify(listener).onDjSessionUpdated(productIdNext, statusNext)
        assertThat(djSessionManager.reflectionCurrentDateRange).isSameAs(dateRanges[1])
    }
}
