package com.tidal.sdk.player.playbackengine.mediasource.loadable

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import assertk.assertions.isTrue
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.StreamingApiRepository
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilege
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerState
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class PlaybackInfoLoadableTest {

    private val streamingSession = mock<StreamingSession.Explicit>()
    private val mediaProduct = MediaProduct(ProductType.TRACK, "123")
    private val forwardingMediaProduct = ForwardingMediaProduct(mediaProduct)
    private val streamingApiRepository = mock<StreamingApiRepository>()
    private val extendedExoPlayerState = mock<ExtendedExoPlayerState>()
    private val playbackPrivilegeProvider = mock<PlaybackPrivilegeProvider> {
        on { this.get(mediaProduct) } doReturn PlaybackPrivilege.OK_ONLINE
    }
    private val playbackInfoLoadable = PlaybackInfoLoadable(
        streamingSession,
        forwardingMediaProduct,
        streamingApiRepository,
        extendedExoPlayerState,
        playbackPrivilegeProvider,
    ) { CoroutineScope(Dispatchers.Default) }

    @Test
    fun cancelLoadShouldNotInteractWithStreamingApiRepository() {
        playbackInfoLoadable.cancelLoad()

        verifyNoInteractions(streamingApiRepository)
    }

    @Test
    fun loadShouldUpdatePlaybackInfoIfSuccess() = runBlocking {
        val streamingSessionId = mock<UUID>()
        val streamingSessionIdString = streamingSessionId.toString()
        whenever(streamingSession.id) doReturn streamingSessionId
        val playbackInfo = mock<PlaybackInfo.Track>()
        whenever(
            streamingApiRepository.getPlaybackInfoForStreaming(
                streamingSessionIdString,
                forwardingMediaProduct,
            ),
        ).thenReturn(playbackInfo)
        val playbackInfoListener = mock<PlaybackInfoListener>()
        whenever(extendedExoPlayerState.playbackInfoListener)
            .thenReturn(playbackInfoListener)

        playbackInfoLoadable.load()

        verify(streamingSession).id
        verify(streamingApiRepository)
            .getPlaybackInfoForStreaming(streamingSessionIdString, forwardingMediaProduct)
        verify(extendedExoPlayerState).playbackInfoListener
        verify(playbackInfoListener)
            .onPlaybackInfoFetched(streamingSession, forwardingMediaProduct, playbackInfo)
        assertThat(playbackInfoLoadable.playbackInfo).isSameAs(playbackInfo)
    }

    @Test
    fun loadShouldThrowExceptionIfError() = runBlocking {
        val streamingSessionId = mock<UUID>()
        val streamingSessionIdString = streamingSessionId.toString()
        val thrown = mock<RuntimeException>()
        whenever(
            streamingApiRepository.getPlaybackInfoForStreaming(
                streamingSessionIdString,
                forwardingMediaProduct,
            ),
        ).thenThrow(thrown)

        val actual = assertThrows<PlaybackInfoFetchException> { playbackInfoLoadable.load() }

        assertThat(actual.requestedMediaProduct).isSameAs(forwardingMediaProduct)
        assertThat(playbackInfoLoadable.playbackInfo).isNull()
        verify(streamingSession).id
        verifyNoMoreInteractions(thrown)
    }

    @Test
    fun cancelLoadShouldCancelLoad() = runBlocking {
        val streamingSessionId = mock<UUID>()
        val streamingSessionIdString = streamingSessionId.toString()
        whenever(
            streamingApiRepository.getPlaybackInfoForStreaming(
                streamingSessionIdString,
                forwardingMediaProduct,
            ),
        )
            .thenAnswer {
                playbackInfoLoadable.cancelLoad()
                mock<PlaybackInfo>()
            }

        playbackInfoLoadable.load()

        assertThat(playbackInfoLoadable.playbackInfo).isNull()
        assertThat(playbackInfoLoadable.reflectionCoroutineScope.isActive).isFalse()
        verify(streamingSession).id
    }

    @Test
    fun cancelLoadShouldCancelLoadAndAllowLoadToBeCalledAgain() = runBlocking {
        val streamingSessionId = mock<UUID>()
        val streamingSessionIdString = streamingSessionId.toString()
        val playbackInfo = mock<PlaybackInfo>()
        val playbackInfo2 = mock<PlaybackInfo>()
        whenever(
            streamingApiRepository.getPlaybackInfoForStreaming(
                streamingSessionIdString,
                forwardingMediaProduct,
            ),
        )
            .thenReturn(playbackInfo, playbackInfo2)

        playbackInfoLoadable.load()
        playbackInfoLoadable.cancelLoad()
        playbackInfoLoadable.load()

        assertThat(playbackInfoLoadable.playbackInfo).isSameAs(playbackInfo2)
        assertThat(playbackInfoLoadable.reflectionCoroutineScope.isActive).isTrue()
        verify(streamingSession).id
    }
}
