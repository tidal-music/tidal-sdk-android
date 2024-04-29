package com.tidal.sdk.player.streamingprivileges

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tidal.sdk.player.streamingprivileges.test", appContext.packageName)
    }
}
