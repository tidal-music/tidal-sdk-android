package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.source.MediaSource
import com.tidal.sdk.player.playbackengine.drm.DrmSessionManagerFactory
import com.tidal.sdk.player.playbackengine.drm.DrmSessionManagerProviderFactory
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/**
 * Creates a [MediaSource] based on the [PlaybackInfo].
 */
@Suppress("LongParameterList")
internal class TidalMediaSourceCreator(
    private val playerProgressiveMediaSourceFactory: PlayerProgressiveMediaSourceFactory,
    private val playerDashMediaSourceFactory: PlayerDashMediaSourceFactory,
    private val playerHlsMediaSourceFactory: PlayerHlsMediaSourceFactory,
    private val playerAuthHlsMediaSourceFactory: PlayerAuthHlsMediaSourceFactory,
    @Suppress("MaxLineLength") private val playerProgressiveOfflineMediaSourceFactory: PlayerProgressiveOfflineMediaSourceFactory, // ktlint-disable max-line-length parameter-wrapping
    private val playerDashOfflineMediaSourceFactory: PlayerDashOfflineMediaSourceFactory,
    private val drmSessionManagerFactory: DrmSessionManagerFactory,
    private val drmSessionManagerProviderFactory: DrmSessionManagerProviderFactory,
) : (MediaItem, PlaybackInfo, Map<String, String?>?) -> MediaSource {

    @Suppress("LongMethod")
    override fun invoke(
        mediaItem: MediaItem,
        playbackInfo: PlaybackInfo,
        extras: Map<String, String?>?,
    ): MediaSource {
        return if (playbackInfo is PlaybackInfo.Offline) {
            when (playbackInfo.manifestMimeType) {
                ManifestMimeType.BTS -> playerProgressiveOfflineMediaSourceFactory.create(
                    mediaItem,
                    playbackInfo.manifest,
                    playbackInfo.storage!!,
                )

                ManifestMimeType.DASH -> playerDashOfflineMediaSourceFactory.create(
                    mediaItem,
                    playbackInfo.manifest,
                    playbackInfo.offlineLicense!!,
                    playbackInfo.storage!!,
                    drmSessionManagerProviderFactory.create(
                        drmSessionManagerFactory.createDrmSessionManagerForOfflinePlay(
                            playbackInfo,
                            extras,
                        ),
                    ),
                )

                else -> throw IllegalArgumentException(
                    "No valid manifestMimeType for offline playback: " +
                        "${playbackInfo.manifestMimeType}",
                )
            }
        } else {
            when (playbackInfo.manifestMimeType) {
                ManifestMimeType.BTS ->
                    playerProgressiveMediaSourceFactory.create(mediaItem, playbackInfo.manifest)

                ManifestMimeType.DASH -> playerDashMediaSourceFactory.create(
                    mediaItem,
                    playbackInfo.manifest,
                    drmSessionManagerProviderFactory.create(
                        drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(
                            playbackInfo,
                            extras,
                        ),
                    ),
                )

                ManifestMimeType.EMU ->
                    if (playbackInfo is PlaybackInfo.UC) {
                        playerAuthHlsMediaSourceFactory.create(
                            mediaItem,
                            playbackInfo.url,
                            drmSessionManagerProviderFactory.create(
                                drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(
                                    playbackInfo,
                                    extras,
                                ),
                            ),
                        )
                    } else {
                        playerHlsMediaSourceFactory.create(
                            mediaItem,
                            playbackInfo.manifest,
                            drmSessionManagerProviderFactory.create(
                                drmSessionManagerFactory.createDrmSessionManagerForOnlinePlay(
                                    playbackInfo,
                                    extras,
                                ),
                            ),
                        )
                    }
            }
        }
    }
}
