package com.tidal.sdk.player.playbackengine

import androidx.media3.common.C
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy.LoadErrorInfo
import assertk.assertThat
import assertk.assertions.isEqualTo
import java.io.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoMoreInteractions

internal class NoRetryLoadErrorHandlingPolicyTest {

    private val loadErrorHandlingPolicy = mock<LoadErrorHandlingPolicy>()
    private val noRetryLoadErrorHandlingPolicy =
        NoRetryLoadErrorHandlingPolicy(loadErrorHandlingPolicy)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(loadErrorHandlingPolicy)

    @Test
    fun getRetryDelayMsForReturnsTimeUnsetForArbitraryLoadErrorInfo() {
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), IOException(), 1)

        val actualRetryDelayMs = noRetryLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(C.TIME_UNSET)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 1, 2, 3, 4, 5, 10])
    fun getRetryDelayMsForReturnsTimeUnsetRegardlessOfErrorCount(errorCount: Int) {
        val loadErrorInfo = LoadErrorInfo(mock(), mock(), IOException(), errorCount)

        val actualRetryDelayMs = noRetryLoadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)

        assertThat(actualRetryDelayMs).isEqualTo(C.TIME_UNSET)
    }
}
