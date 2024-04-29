package com.tidal.sdk.player.playbackengine.view

import android.view.View.MeasureSpec
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

internal class AspectRatioAdjustingSurfaceViewTest {

    private val aspectRatioAdjustingSurfaceView = AspectRatioAdjustingSurfaceView(
        InstrumentationRegistry.getInstrumentation().targetContext,
    )

    @Test
    fun assertAspectRatio1To1VideoSmallerThanScreen() = assertAspectRatio(
        AspectRatioAdjustingSurfaceView.SuggestedDimensions(4, 4),
    )

    @Test
    fun assertAspectRatio1To2VideoSmallerThanScreen() = assertAspectRatio(
        AspectRatioAdjustingSurfaceView.SuggestedDimensions(7, 14),
    )

    @Test
    fun assertAspectRatio2To1VideoSmallerThanScreen() = assertAspectRatio(
        AspectRatioAdjustingSurfaceView.SuggestedDimensions(5, 2),
    )

    @Test
    fun assertAspectRatio1To1VideoLargerThanScreen() = assertAspectRatio(
        AspectRatioAdjustingSurfaceView.SuggestedDimensions(600, 600),
    )

    @Test
    fun assertAspectRatio1To2VideoLargerThanScreen() = assertAspectRatio(
        AspectRatioAdjustingSurfaceView.SuggestedDimensions(900, 1800),
    )

    @Test
    fun assertAspectRatio2To1VideoLargerThanScreen() = assertAspectRatio(
        AspectRatioAdjustingSurfaceView.SuggestedDimensions(900, 450),
    )

    private fun assertAspectRatio(
        suggestedDimensions: AspectRatioAdjustingSurfaceView.SuggestedDimensions<Int>,
    ) {
        aspectRatioAdjustingSurfaceView.suggestedVideoDimen = suggestedDimensions

        aspectRatioAdjustingSurfaceView.measure(
            MeasureSpec.makeMeasureSpec(300, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(300, MeasureSpec.AT_MOST),
        )

        assertThat(
            aspectRatioAdjustingSurfaceView.measuredWidth.toDouble() /
                aspectRatioAdjustingSurfaceView.measuredHeight,
        ).isEqualTo(suggestedDimensions.widthPx.toDouble() / suggestedDimensions.heightPx)
    }
}
