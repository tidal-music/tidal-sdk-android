package com.tidal.sdk.player.playbackengine.volume

import android.os.Handler
import assertk.assertThat
import assertk.assertions.isCloseTo
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

private const val FADE_MS = 200L
private const val TICK_MS = 50L
private const val TOLERANCE = 0.0001f

internal class FadeOutTest {

    private val handler = mock<Handler>()
    private var nowMillis = 1_000L
    private var steps = 0
    private var silences = 0
    private var stepLevel = 1f
    private val fadeOut: FadeOut =
        FadeOut(
            handler,
            { nowMillis },
            {
                steps++
                stepLevel = fadeOut.level
            },
            { silences++ },
        )

    @Test
    fun isAtFullLevelUntilStarted() {
        assertThat(fadeOut.isActive).isFalse()
        assertThat(fadeOut.level).isCloseTo(1f, TOLERANCE)
    }

    @Test
    fun stepsDownAndKeepsTicking() {
        fadeOut.start(FADE_MS)
        nowMillis += FADE_MS / 2

        assertThat(fadeOut.level).isCloseTo(0.5f, TOLERANCE)
        assertThat(steps).isEqualTo(1)
        verify(handler).postDelayed(any(), eq(TICK_MS))
    }

    @Test
    fun reportsSilenceOnceDone() {
        fadeOut.start(FADE_MS)
        val ticker = argumentCaptor<Runnable>()
        verify(handler).postDelayed(ticker.capture(), eq(TICK_MS))
        nowMillis += FADE_MS

        ticker.firstValue.run()

        assertThat(silences).isEqualTo(1)
        assertThat(steps).isEqualTo(2)
        assertThat(stepLevel).isCloseTo(0f, TOLERANCE)
    }

    @Test
    fun restartingCarriesOnFromWhereItHadGot() {
        fadeOut.start(FADE_MS)
        nowMillis += FADE_MS / 2
        fadeOut.start(FADE_MS)
        nowMillis += FADE_MS / 2

        assertThat(fadeOut.level).isCloseTo(0.25f, TOLERANCE)
    }

    @Test
    fun cancelReportsWhetherOneWasUnderWay() {
        assertThat(fadeOut.cancel()).isFalse()
        verify(handler, never()).removeCallbacks(any())

        fadeOut.start(FADE_MS)

        assertThat(fadeOut.cancel()).isTrue()
        assertThat(fadeOut.isActive).isFalse()
        assertThat(fadeOut.level).isCloseTo(1f, TOLERANCE)
    }
}
