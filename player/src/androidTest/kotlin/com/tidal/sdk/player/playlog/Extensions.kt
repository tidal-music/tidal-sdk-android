package com.tidal.sdk.player.playlog

import assertk.Assert
import assertk.assertions.isCloseTo
import kotlin.math.absoluteValue

internal fun Assert<Double>.isAssetPositionEqualTo(targetPosition: Double) =
    isCloseTo(targetPosition, 0.5)

internal fun Double.isAssetPositionEqualTo(targetPosition: Double) =
    (this - targetPosition).absoluteValue < 0.5
