package com.tidal.sdk.player.playbackengine.drm

import android.util.Log
import androidx.media3.common.Format
import androidx.media3.exoplayer.dash.manifest.DashManifest
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionEventListener
import androidx.media3.exoplayer.drm.OfflineLicenseHelper
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.playbackengine.dash.DashManifestFactory
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Implements the "lazy-on-first-play" persistent DRM license strategy.
 *
 * While a track is streamed normally, [acquireAndStoreAsync] fetches an *offline* (persistent)
 * Widevine license in the background and stores its key-set ID via [DrmKeySetStore]. On a later
 * play of the same content, [getValidKeySetId] returns that key-set ID if it is still valid,
 * allowing the caller to put the playback session into offline mode so the disk-cached media can be
 * decrypted with no network license request.
 *
 * Notes/limitations:
 * - Only DASH content is supported (audio tracks); other manifest types are skipped.
 * - The Tidal license server must be willing to issue a persistent (offline) license on the
 *   streaming license URL. If it does not, acquisition fails gracefully and nothing is cached, so
 *   playback continues to fetch a streaming license on every play as before.
 * - Validity is computed from the license duration captured at acquisition time, with a safety
 *   buffer ([MINIMUM_REMAINING_LICENSE_SEC]). A stale/expired key set is dropped on lookup.
 */
@Suppress("LongParameterList")
internal class OfflineLicenseManager(
    private val defaultDrmSessionManagerBuilder: DefaultDrmSessionManager.Builder,
    private val tidalMediaDrmCallbackFactory: TidalMediaDrmCallbackFactory,
    private val dashManifestFactory: DashManifestFactory,
    private val drmKeySetStore: DrmKeySetStore,
    private val trueTimeWrapper: TrueTimeWrapper,
    private val coroutineScope: CoroutineScope,
) {

    /**
     * Returns a still-valid offline key-set ID for [playbackInfo], or null if none is stored or it
     * is (near) expired. Expired entries are removed.
     */
    fun getValidKeySetId(playbackInfo: PlaybackInfo): ByteArray? {
        if (!ENABLED) {
            return null
        }
        val contentKey = playbackInfo.contentKey() ?: return null
        val entry = drmKeySetStore.get(contentKey) ?: return null
        val ageSec = (trueTimeWrapper.currentTimeMillis - entry.acquiredAtMs) / MILLIS_PER_SECOND
        val remainingSec = entry.licenseDurationSec - ageSec
        if (remainingSec <= MINIMUM_REMAINING_LICENSE_SEC) {
            drmKeySetStore.remove(contentKey)
            Log.d(DRM_LOG_TAG, "Dropping expired offline license for $contentKey")
            return null
        }
        Log.d(
            DRM_LOG_TAG,
            "Reusing offline license for $contentKey, remainingSec=$remainingSec (no network)",
        )
        return entry.keySetId
    }

    /**
     * Lazily fetches and persists an offline license for [playbackInfo] so the next play can be
     * decrypted offline. No-op if disabled, unsupported, or already cached.
     */
    @Suppress("TooGenericExceptionCaught")
    fun acquireAndStoreAsync(playbackInfo: PlaybackInfo, extras: Extras?) {
        if (!ENABLED) {
            return
        }
        val contentKey = playbackInfo.contentKey() ?: return
        val licenseUrl = playbackInfo.licenseUrl
        if (licenseUrl.isNullOrEmpty()) {
            return
        }
        if (playbackInfo.manifestMimeType != ManifestMimeType.DASH) {
            return
        }
        if (drmKeySetStore.get(contentKey) != null) {
            return
        }
        coroutineScope.launch {
            try {
                acquireAndStore(contentKey, playbackInfo, licenseUrl, extras)
            } catch (e: Exception) {
                Log.d(
                    DRM_LOG_TAG,
                    "Offline license acquisition failed for $contentKey: ${e.message}",
                )
            }
        }
    }

    private fun acquireAndStore(
        contentKey: String,
        playbackInfo: PlaybackInfo,
        licenseUrl: String,
        extras: Extras?,
    ) {
        val drmFormat =
            dashManifestFactory.create(playbackInfo.manifest).findDrmFormat()
                ?: run {
                    Log.d(DRM_LOG_TAG, "No DRM init data in manifest for $contentKey")
                    return
                }
        val helper = createOfflineLicenseHelper(licenseUrl, playbackInfo.streamingSessionId, extras)
        try {
            val keySetId = helper.downloadLicense(drmFormat)
            if (keySetId == null || keySetId.isEmpty()) {
                Log.d(DRM_LOG_TAG, "Server did not return a persistable license for $contentKey")
                return
            }
            val licenseDurationSec = helper.getLicenseDurationRemainingSec(keySetId).first
            if (licenseDurationSec <= MINIMUM_REMAINING_LICENSE_SEC) {
                Log.d(
                    DRM_LOG_TAG,
                    "Persistent license too short-lived for $contentKey, not storing",
                )
                helper.releaseLicense(keySetId)
                return
            }
            drmKeySetStore.put(
                contentKey,
                DrmKeySetStore.Entry(
                    keySetId = keySetId,
                    acquiredAtMs = trueTimeWrapper.currentTimeMillis,
                    licenseDurationSec = licenseDurationSec,
                ),
            )
            Log.d(
                DRM_LOG_TAG,
                "Stored offline license for $contentKey, licenseDurationSec=$licenseDurationSec",
            )
        } finally {
            helper.release()
        }
    }

    private fun createOfflineLicenseHelper(
        licenseUrl: String,
        streamingSessionId: String,
        extras: Extras?,
    ): OfflineLicenseHelper {
        val callback =
            tidalMediaDrmCallbackFactory.create(
                licenseUrl,
                streamingSessionId,
                DrmMode.Streaming,
                extras,
            )
        val drmSessionManager = defaultDrmSessionManagerBuilder.build(callback)
        return OfflineLicenseHelper(drmSessionManager, DrmSessionEventListener.EventDispatcher())
    }

    @Suppress("NestedBlockDepth")
    private fun DashManifest.findDrmFormat(): Format? {
        for (periodIndex in 0 until periodCount) {
            val period = getPeriod(periodIndex)
            for (adaptationSet in period.adaptationSets) {
                for (representation in adaptationSet.representations) {
                    if (representation.format.drmInitData != null) {
                        return representation.format
                    }
                }
            }
        }
        return null
    }

    private fun PlaybackInfo.contentKey(): String? =
        when (this) {
            is PlaybackInfo.Track -> manifestHash
            is PlaybackInfo.Video -> manifestHash
            else -> null
        }

    private companion object {
        /** Master kill-switch for the persistent license cache. */
        const val ENABLED = true
        const val DRM_LOG_TAG = "TidalWidevineDrm"
        const val MILLIS_PER_SECOND = 1000L

        /** Safety buffer; treat licenses with less remaining time than this as unusable. */
        const val MINIMUM_REMAINING_LICENSE_SEC = 60L
    }
}
