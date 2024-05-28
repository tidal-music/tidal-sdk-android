package com.tidal.auth

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
internal class ExampleInstrumentedTest {

    @Test
    fun comparePackageNames() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        assertEquals(instrumentationContext.packageName, appContext.packageName)
    }
}
