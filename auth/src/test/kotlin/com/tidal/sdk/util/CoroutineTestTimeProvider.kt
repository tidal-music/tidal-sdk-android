package com.tidal.sdk.util

import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.util.CoroutineTestTimeProvider.Mode
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher

/**
 * [TimeProvider] implementation that can be used to control time flow inside a
 * coroutine by seconds. Since all classes inside `TidalAuth` use a [TimeProvider] to
 * determine current or elapsed times, even non-coroutine code will be synced with
 * the time in this [TimeProvider]. Using it with [runTest] enables us to
 * control and measure time without having to wait the actual time frame.
 * @param dispatcher The [TestDispatcher] to use.
 * @param mode The [Mode] to use. Default is [Mode.SECONDS], so when running, time will tick
 * forward 1 second at a time. If you need it more fine-grained, use [Mode.MILLISECONDS].
 */
class CoroutineTestTimeProvider(
    private val dispatcher: TestDispatcher,
    val mode: Mode = Mode.SECONDS,
) : TimeProvider {

    override val now
        get() = getTimeSeconds()

    private var timeJob: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getTimeSeconds(): Long = (dispatcher.scheduler.currentTime) / 1000

    fun startTimeFor(scope: CoroutineScope, units: Int) {
        if (mode == Mode.SECONDS) {
            startTimeForSeconds(scope, units)
        } else {
            startTimeForMillis(scope, units)
        }
    }

    private fun startTimeForSeconds(scope: CoroutineScope, units: Int) {
        var unitsElapsed = 1
        timeJob = scope.launch {
            while (unitsElapsed < units) {
                delay(1000)
                dispatcher.scheduler.advanceTimeBy(1.toDuration(DurationUnit.SECONDS))
                unitsElapsed += 1
            }
        }
    }

    private fun startTimeForMillis(scope: CoroutineScope, units: Int) {
        var unitsElapsed = 1
        timeJob = scope.launch {
            while (unitsElapsed < units) {
                delay(1)
                dispatcher.scheduler.advanceTimeBy(1.toDuration(DurationUnit.MILLISECONDS))
                unitsElapsed += 1
            }
        }
    }

    enum class Mode {
        SECONDS,
        MILLISECONDS,
    }
}
