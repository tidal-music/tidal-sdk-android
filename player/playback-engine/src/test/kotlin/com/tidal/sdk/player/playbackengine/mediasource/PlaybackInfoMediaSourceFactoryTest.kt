package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadable
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableFactory
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableLoaderCallbackFactory
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class PlaybackInfoMediaSourceFactoryTest {

    private val playerLoadErrorHandlingPolicy = mock<LoadErrorHandlingPolicy>()
    private val noRetryLoadErrorHandlingPolicy = mock<LoadErrorHandlingPolicy>()
    private val playbackInfoLoadableFactory = mock<PlaybackInfoLoadableFactory>()
    private val playbackInfoLoadableLoaderCallbackFactory =
        mock<PlaybackInfoLoadableLoaderCallbackFactory>()
    private val playbackInfoMediaSourceFactory =
        PlaybackInfoMediaSourceFactory(
            playerLoadErrorHandlingPolicy,
            noRetryLoadErrorHandlingPolicy,
            playbackInfoLoadableFactory,
            playbackInfoLoadableLoaderCallbackFactory,
        )

    /**
     * Asserts the policy selected per product type. Combined with
     * NoRetryLoadErrorHandlingPolicyTest (no-retry policy returns C.TIME_UNSET),
     * PlaybackInfoLoadableLoaderCallbackTest (C.TIME_UNSET -> DONT_RETRY_FATAL), and
     * PlayerLoadErrorHandlingPolicyTest (player policy retries 5xx/429), this proves TRACK/VIDEO
     * PlaybackInfo fetches are fatal on first error while BROADCAST/UC still retry.
     */
    @ParameterizedTest
    @MethodSource("productTypeToExpectedPolicy")
    fun createSelectsPolicyForProductType(
        productType: ProductType,
        expectedPolicyIsNoRetry: Boolean,
    ) {
        val streamingSession = mock<StreamingSession.Explicit>()
        val forwardingMediaProduct = ForwardingMediaProduct(MediaProduct(productType, "123"))
        whenever(playbackInfoLoadableFactory.create(streamingSession, forwardingMediaProduct))
            .thenReturn(mock<PlaybackInfoLoadable>())

        val playbackInfoMediaSource =
            playbackInfoMediaSourceFactory.create(streamingSession, forwardingMediaProduct)

        val expectedPolicy =
            if (expectedPolicyIsNoRetry) {
                noRetryLoadErrorHandlingPolicy
            } else {
                playerLoadErrorHandlingPolicy
            }
        assertThat(playbackInfoMediaSource.reflectionLoadErrorHandlingPolicy)
            .isSameInstanceAs(expectedPolicy)
    }

    @Test
    fun createForwardsMediaProduct() {
        val streamingSession = mock<StreamingSession.Explicit>()
        val forwardingMediaProduct = ForwardingMediaProduct(MediaProduct(ProductType.TRACK, "123"))
        whenever(playbackInfoLoadableFactory.create(streamingSession, forwardingMediaProduct))
            .thenReturn(mock<PlaybackInfoLoadable>())

        val playbackInfoMediaSource =
            playbackInfoMediaSourceFactory.create(streamingSession, forwardingMediaProduct)

        assertThat(playbackInfoMediaSource.forwardingMediaProduct)
            .isSameInstanceAs(forwardingMediaProduct)
    }

    companion object {

        @JvmStatic
        fun productTypeToExpectedPolicy() =
            listOf(
                Arguments.of(ProductType.TRACK, true),
                Arguments.of(ProductType.VIDEO, true),
                Arguments.of(ProductType.BROADCAST, false),
                Arguments.of(ProductType.UC, false),
            )
    }
}
