package com.tidal.sdk.util

import com.tidal.sdk.auth.util.TimeProvider
import io.fluidsonic.time.ManualClock
import io.fluidsonic.time.advance
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlinx.datetime.Clock

class TestTimeProvider : TimeProvider {
    val clock = ManualClock().apply { set(Clock.System.now()) }

    override val now: Long
        get() = clock.now().epochSeconds

    fun advanceSeconds(seconds: Int) =
        clock.advance(seconds.toDuration(DurationUnit.SECONDS)).also { clock.set(it) }
}
