package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import android.content.SharedPreferences
import assertk.assertThat
import assertk.assertions.isNull
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.Progress
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Answers
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class PlaybackReportHandlerTest {

    private val initialPlaybackReportActualProductId = "itemId"
    private val initialPlaybackItemType = "track"
    private val initialPlaybackProgressStop = 2
    private val initialPlaybackDuration = Int.MIN_VALUE
    private val initialPlaybackSourceType = "sourceType"
    private val initialPlaybackSourceId = "sourceId"
    private val initialPlaybackSourceInfo = mapOf(
        PlaybackReport.Handler.reflectionKEY_SOURCE_INFO_TYPE to initialPlaybackSourceType,
        PlaybackReport.Handler.reflectionKEY_SOURCE_INFO_ID to initialPlaybackSourceId,
    )
    private val initialPlaybackReport = mock<PlaybackReport> {
        on { actualProductId } doReturn initialPlaybackReportActualProductId
        on { itemType } doReturn initialPlaybackItemType
        on { duration } doReturn initialPlaybackDuration
        on { progressStop } doReturn initialPlaybackProgressStop
        on { sourceInfo } doReturn initialPlaybackSourceInfo
    }
    private val gson = mock<Gson>()
    private val sharedPreferences = mock<SharedPreferences>()
    private val eventReporter = mock<EventReporter>()
    private lateinit var playbackReportHandler: PlaybackReport.Handler

    @BeforeEach
    fun beforeEach() {
        val persistedPlaybackReportString = "persistedPlaybackReportString"
        whenever(
            sharedPreferences.getString(PlaybackReport.Handler.reflectionKEY_PLAYBACK_REPORT, null),
        ) doReturn persistedPlaybackReportString
        whenever(gson.fromJson(persistedPlaybackReportString, PlaybackReport::class.java))
            .thenReturn(initialPlaybackReport)
        val editor = mock<SharedPreferences.Editor>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(sharedPreferences.edit()) doReturn editor

        playbackReportHandler = PlaybackReport.Handler(gson, sharedPreferences, eventReporter)

        verify(sharedPreferences)
            .getString(PlaybackReport.Handler.reflectionKEY_PLAYBACK_REPORT, null)
        verify(gson).fromJson(persistedPlaybackReportString, PlaybackReport::class.java)
        verify(initialPlaybackReport).actualProductId
        verify(initialPlaybackReport).progressStop
        verify(initialPlaybackReport).duration
        verify(initialPlaybackReport).itemType
        verify(initialPlaybackReport).sourceInfo
        verify(eventReporter).report(
            Progress.Payload(
                Progress.Payload.Playback(
                    initialPlaybackReportActualProductId,
                    initialPlaybackProgressStop,
                    initialPlaybackDuration,
                    ProductType.TRACK,
                    Progress.Payload.Playback.Source(
                        initialPlaybackSourceType,
                        initialPlaybackSourceId,
                    ),
                ),
            ),
        )
        verify(sharedPreferences).edit()
        editor.inOrder {
            verify(editor).remove(PlaybackReport.Handler.reflectionKEY_PLAYBACK_REPORT)
            verify(editor).apply()
        }
        assertThat(playbackReportHandler.currentPlaybackReport).isNull()
        reset(sharedPreferences)
    }

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(initialPlaybackReport, gson, sharedPreferences, eventReporter)

    @Test
    fun testSettingCurrentPlaybackReportToNull() {
        val editor = mock<SharedPreferences.Editor>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(sharedPreferences.edit()) doReturn editor

        playbackReportHandler.currentPlaybackReport = null

        verify(sharedPreferences).edit()
        editor.inOrder {
            verify(editor).remove(PlaybackReport.Handler.reflectionKEY_PLAYBACK_REPORT)
            verify(editor).apply()
        }
        verifyNoMoreInteractions(editor)
    }

    @Test
    fun testSettingCurrentPlaybackReportToNonNull() {
        val editor = mock<SharedPreferences.Editor>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(sharedPreferences.edit()) doReturn editor
        val newPlaybackReport = mock<PlaybackReport>()
        val newPlaybackReportJson = "newPlaybackReportJson"
        whenever(gson.toJson(newPlaybackReport)) doReturn newPlaybackReportJson

        playbackReportHandler.currentPlaybackReport = newPlaybackReport

        verify(sharedPreferences).edit()
        verify(gson).toJson(newPlaybackReport)
        editor.inOrder {
            verify(editor).putString(
                PlaybackReport.Handler.reflectionKEY_PLAYBACK_REPORT,
                newPlaybackReportJson,
            )
            verify(editor).apply()
        }
        verifyNoMoreInteractions(editor, newPlaybackReport)
    }
}
