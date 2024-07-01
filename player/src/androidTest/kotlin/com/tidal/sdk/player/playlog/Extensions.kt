package com.tidal.sdk.player.playlog

import assertk.Assert
import assertk.assertions.isCloseTo
import junit.framework.AssertionFailedError

internal fun Assert<Double>.isAssetPositionEqualTo(targetPosition: Double) =
    isCloseTo(targetPosition, 0.5)

@Suppress("ThrowsCount")
internal fun <T> Iterable<T>.combinedPassAllOf(
    vararg checks: Pair<Int, T.() -> Unit>,
) {
    val passesLeft = checks.map { it.first }.toMutableList()
    val throwableMessageMap = mutableMapOf<Int, MutableList<String>>()
    forEachIndexed { subjectIndex, subject ->
        var checkIndex = -1
        while (checkIndex < checks.size) {
            try {
                val check = checks[++checkIndex].second
                subject.check()
                if (passesLeft[checkIndex] <= 0) {
                    throw AssertionFailedError(
                        "Check at index $checkIndex passed more than desired amount of times (${checks[checkIndex].first})" // ktlint-disable max-line-length
                    )
                }
                passesLeft[checkIndex]--
                throwableMessageMap.remove(subjectIndex)
                checkIndex = checks.size
            } catch (throwable: Throwable) {
                if (!throwableMessageMap.containsKey(subjectIndex)) {
                    throwableMessageMap[subjectIndex] = mutableListOf()
                }
                throwableMessageMap.getValue(subjectIndex).add(throwable.message!!)
            }
        }
    }
    if (throwableMessageMap.isNotEmpty()) {
        throw AssertionFailedError("Failed check(s): $throwableMessageMap")
    }
    val index = passesLeft.indexOfFirst { it != 0 }
    if (index != -1) {
        throw AssertionFailedError(
            "Missed check: index $index, wanted ${checks[index].first}, missed ${passesLeft[index]}"
        )
    }
}
