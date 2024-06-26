package com.tidal.sdk.player.playlog

import assertk.Assert
import assertk.assertions.isCloseTo

internal fun Assert<Double>.isAssetPositionEqualTo(targetPosition: Double) =
    isCloseTo(targetPosition, 0.5)
