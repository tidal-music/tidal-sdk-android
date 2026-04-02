package com.tidal.sdk.util

import com.tidal.sdk.auth.util.TimeProvider
import io.fluidsonic.time.ManualClock
import io.fluidsonic.time.advance
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
class TestTimeProvider : TimeProvider {
    val clock = ManualClock().apply { set(kotlin.time.Clock.System.now()) }

    override val now: Long
        get() = clock.now().epochSeconds

    fun advanceSeconds(seconds: Int) =
        clock.advance(seconds.toDuration(DurationUnit.SECONDS)).also { clock.set(it) }
}
