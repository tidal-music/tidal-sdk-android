package com.tidal.sdk.player.playbackengine.mediasource.loadable

import androidx.media3.exoplayer.upstream.Loader
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.StreamingApiRepository
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.playbackengine.offline.OfflineExpiredException
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilege
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerState
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking

/**
 * A [Loader.Loadable] that loads [PlaybackInfo] with the help of our streaming api.
 */
internal class PlaybackInfoLoadable(
    private val streamingSession: StreamingSession,
    private val forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>,
    private val streamingApiRepository: StreamingApiRepository,
    private val extendedExoPlayerState: ExtendedExoPlayerState,
    private val playbackPrivilegeProvider: PlaybackPrivilegeProvider,
    private val coroutineScopeF: () -> CoroutineScope,
) : Loader.Loadable {

    private var coroutineScope: CoroutineScope? = null

    var playbackInfo: PlaybackInfo? = null
        private set

    override fun cancelLoad() = coroutineScope?.cancel(
        CancellationException(
            null,
            PlaybackInfoFetchException.Cancellation(forwardingMediaProduct)
        ),
    ) ?: Unit

    @SuppressWarnings("TooGenericExceptionCaught")
    override fun load() {
        try {
            coroutineScopeF().also {
                coroutineScope = it
                runBlocking(it.coroutineContext) {
                    when (playbackPrivilegeProvider.get(forwardingMediaProduct.delegate)) {
                        PlaybackPrivilege.OK_ONLINE ->
                            streamingApiRepository.getPlaybackInfoForStreaming(
                                streamingSession.id.toString(),
                                forwardingMediaProduct,
                            )

                        PlaybackPrivilege.OK_OFFLINE ->
                            streamingApiRepository.getPlaybackInfoForOfflinePlayback(
                                streamingSession.id.toString(),
                                forwardingMediaProduct,
                            )

                        PlaybackPrivilege.OK_LOCAL -> PlaybackInfo.LocalTrack(
                            id = forwardingMediaProduct.productId,
                            url = forwardingMediaProduct.productId,
                            streamingSessionId = streamingSession.id.toString(),
                            manifestMimeType = ManifestMimeType.EMU,
                            manifest = "",
                            licenseSecurityToken = null,
                            albumReplayGain = 0f,
                            albumPeakAmplitude = 0f,
                            trackReplayGain = 0f,
                            trackPeakAmplitude = 0f,
                            offlineRevalidateAt = -1L,
                            offlineValidUntil = -1L,
                        )

                        PlaybackPrivilege.OFFLINE_EXPIRED -> throw OfflineExpiredException()
                    }.also {
                        playbackInfo = it
                        extendedExoPlayerState.playbackInfoListener?.onPlaybackInfoFetched(
                            streamingSession,
                            forwardingMediaProduct,
                            it,
                        )
                    }
                }
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is CancellationException, is InterruptedException -> Unit
                else -> throw PlaybackInfoFetchException.Error(forwardingMediaProduct, throwable)
            }
        }
    }
}
