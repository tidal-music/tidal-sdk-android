package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.common.DrmInitData
import androidx.media3.common.Format
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.OfflineLicenseHelper
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class OfflineLicenseProviderDefaultTest {

    private val drmSessionManager = mock<DefaultDrmSessionManager>()
    private val drmSessionManagerBuilder = mock<DefaultDrmSessionManager.Builder>()
    private val drmCallback = mock<TidalMediaDrmCallback>()
    private val drmCallbackFactory = mock<TidalMediaDrmCallbackFactory>()
    private val offlineLicenseHelper = mock<OfflineLicenseHelper>()
    private val offlineLicenseHelperFactory = mock<OfflineLicenseHelperFactory>()
    private val base64Codec = mock<Base64Codec>()
    private val offlineLicenseProvider =
        OfflineLicenseProviderDefault(
            drmSessionManagerBuilder,
            drmCallbackFactory,
            offlineLicenseHelperFactory,
            base64Codec,
            Dispatchers.Unconfined,
        )

    @Test
    fun acquireOfflineLicenseReturnsNullForDrmFreeFormat() = runBlocking {
        val playbackInfo = mock<PlaybackInfo.Track>()
        val format = mock<Format> { on { drmInitData } doReturn null }

        val actual = offlineLicenseProvider.acquireOfflineLicense(playbackInfo, format)

        assertThat(actual).isNull()
        verify(offlineLicenseHelperFactory, never()).create(any())
    }

    @Test
    fun acquireOfflineLicenseReturnsEncodedPersistentKeySet() = runBlocking {
        val playbackInfo =
            mock<PlaybackInfo.Track> {
                on { licenseUrl } doReturn "license-url"
                on { streamingSessionId } doReturn "session-id"
            }
        val format = mock<Format> { on { drmInitData } doReturn mock<DrmInitData>() }
        val keySetId = byteArrayOf(1, 2, 3)
        arrangeHelper(DrmMode.OfflineAcquisition)
        whenever(offlineLicenseHelper.downloadLicense(format)).thenReturn(keySetId)
        whenever(base64Codec.encode(keySetId)).thenReturn("encoded-license".toByteArray())

        val actual = offlineLicenseProvider.acquireOfflineLicense(playbackInfo, format)

        assertThat(actual).isEqualTo("encoded-license")
        verify(offlineLicenseHelper).release()
    }

    @Test
    fun renewOfflineLicenseReturnsEncodedReplacementKeySet() = runBlocking {
        val playbackInfo =
            mock<PlaybackInfo.Video> {
                on { licenseUrl } doReturn "license-url"
                on { streamingSessionId } doReturn "session-id"
            }
        val oldKeySetId = byteArrayOf(1)
        val renewedKeySetId = byteArrayOf(2)
        arrangeHelper(DrmMode.OfflineRenewal)
        whenever(base64Codec.decode("old-license".toByteArray())).thenReturn(oldKeySetId)
        whenever(offlineLicenseHelper.renewLicense(oldKeySetId)).thenReturn(renewedKeySetId)
        whenever(base64Codec.encode(renewedKeySetId)).thenReturn("renewed-license".toByteArray())

        val actual = offlineLicenseProvider.renewOfflineLicense(playbackInfo, "old-license")

        assertThat(actual).isEqualTo("renewed-license")
        verify(offlineLicenseHelper).release()
    }

    @Test
    fun releaseOfflineLicenseReleasesDecodedKeySet() = runBlocking {
        val keySetId = byteArrayOf(1, 2, 3)
        whenever(drmCallbackFactory.create("", "", DrmMode.OfflineRelease, null))
            .thenReturn(drmCallback)
        whenever(drmSessionManagerBuilder.build(drmCallback)).thenReturn(drmSessionManager)
        whenever(offlineLicenseHelperFactory.create(drmSessionManager))
            .thenReturn(offlineLicenseHelper)
        whenever(base64Codec.decode("offline-license".toByteArray())).thenReturn(keySetId)

        offlineLicenseProvider.releaseOfflineLicense("offline-license")

        verify(offlineLicenseHelper).releaseLicense(keySetId)
        verify(offlineLicenseHelper).release()
    }

    @Test
    fun acquireOfflineLicenseReleasesHelperWhenAcquisitionFails() {
        val playbackInfo =
            mock<PlaybackInfo.Track> {
                on { licenseUrl } doReturn "license-url"
                on { streamingSessionId } doReturn "session-id"
            }
        val format = mock<Format> { on { drmInitData } doReturn mock<DrmInitData>() }
        val expected = IllegalStateException("failed")
        arrangeHelper(DrmMode.OfflineAcquisition)
        whenever(offlineLicenseHelper.downloadLicense(format)).thenThrow(expected)

        val actual =
            assertThrows<IllegalStateException> {
                runBlocking { offlineLicenseProvider.acquireOfflineLicense(playbackInfo, format) }
            }

        assertThat(actual).isEqualTo(expected)
        verify(offlineLicenseHelper).release()
    }

    private fun arrangeHelper(mode: DrmMode) {
        whenever(drmCallbackFactory.create("license-url", "session-id", mode, null))
            .thenReturn(drmCallback)
        whenever(drmSessionManagerBuilder.build(drmCallback)).thenReturn(drmSessionManager)
        whenever(offlineLicenseHelperFactory.create(drmSessionManager))
            .thenReturn(offlineLicenseHelper)
    }
}
