package com.tidal.sdk.player.playbackengine.view

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import android.view.View.MeasureSpec
import com.tidal.sdk.player.playbackengine.PlaybackEngine
import kotlin.properties.Delegates

/**
 * A [SurfaceView] which automatically updates its size to be as big as possible while respecting
 * its measurement requirements and, if possible, also sticking to the suggested aspect ratio as per
 * [suggestedVideoDimen].
 *
 * If not getting measurement restrictions (both width and height [MeasureSpec] are
 * [MeasureSpec.UNSPECIFIED]), the measurements forwarded will be the ones in [suggestedVideoDimen].
 *
 * @see PlaybackEngine.videoSurfaceView
 */
class AspectRatioAdjustingSurfaceView : SurfaceView {

    /**
     * Suggested video dimensions.
     */
    var suggestedVideoDimen by Delegates.observable(
        SuggestedDimensions(
            0,
            0,
        ),
    ) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            post { requestLayout() }
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
        super(context, attrs, defStyleAttr, defStyleRes)

    @Suppress("ComplexMethod", "LongMethod")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val toExactSizeOrZero = { measureSpec: Int ->
            if (MeasureSpec.getMode(measureSpec) == MeasureSpec.EXACTLY) {
                MeasureSpec.getSize(measureSpec)
            } else {
                0
            }
        }
        if (suggestedVideoDimen.run { widthPx == 0 || heightPx == 0 }) {
            setMeasuredDimension(
                toExactSizeOrZero(widthMeasureSpec),
                toExactSizeOrZero(heightMeasureSpec),
            )
            return
        }
        val targetWidthPx = when (suggestedVideoDimen.allowUpscaling) {
            false -> suggestedVideoDimen.widthPx
            true -> if (suggestedVideoDimen.run { widthPx >= heightPx }) {
                Int.MAX_VALUE
            } else {
                suggestedVideoDimen.run { Int.MAX_VALUE / heightPx * widthPx }
            }
        }
        val targetHeightPx = suggestedVideoDimen.run { targetWidthPx / widthPx * heightPx }
        val targetAspectRatio = targetWidthPx.toFloat() / targetHeightPx
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        var width = 0
        var height = 0
        val calculateWidthRespectingMeasureSpecAndThenAspectRatio = {
            width = (targetAspectRatio * height).toInt().run {
                when (widthMeasureSpecMode) {
                    MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
                    MeasureSpec.AT_MOST -> coerceAtMost(MeasureSpec.getSize(widthMeasureSpec))
                    MeasureSpec.UNSPECIFIED -> this
                    else -> throw IllegalArgumentException(
                        "Unsupported width MeasureSpec mode $widthMeasureSpecMode",
                    )
                }
            }
        }
        val calculateHeightRespectingMeasureSpecAndThenAspectRatio = {
            height = (width.toFloat() / targetAspectRatio).toInt().run {
                when (heightMeasureSpecMode) {
                    MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
                    MeasureSpec.AT_MOST -> coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
                    MeasureSpec.UNSPECIFIED -> this
                    else -> throw IllegalArgumentException(
                        "Unsupported height MeasureSpec mode $heightMeasureSpecMode",
                    )
                }
            }
        }
        width = when (widthMeasureSpecMode) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            MeasureSpec.AT_MOST ->
                targetWidthPx.coerceAtMost(MeasureSpec.getSize(widthMeasureSpec))

            MeasureSpec.UNSPECIFIED -> targetWidthPx
            else -> throw IllegalArgumentException(
                "Unexpected width MeasureSpec mode $widthMeasureSpecMode",
            )
        }
        calculateHeightRespectingMeasureSpecAndThenAspectRatio()
        if (width.toFloat() / height != targetAspectRatio) {
            calculateWidthRespectingMeasureSpecAndThenAspectRatio()
        }
        setMeasuredDimension(width, height)
    }

    data class SuggestedDimensions<T>(
        val widthPx: T,
        val heightPx: T,
        val allowUpscaling: Boolean = true,
    )
}
