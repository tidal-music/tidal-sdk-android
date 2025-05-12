package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import android.media.MediaDrm
import androidx.media3.common.C
import androidx.media3.exoplayer.drm.ExoMediaDrm
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import com.tidal.sdk.player.events.model.PlaybackStatistics
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class VersionedCdmCalculatorTest {

    private val exoMediaDrmProvider = mock<ExoMediaDrm.Provider>()
    private val versionedCdmCalculator = VersionedCdm.Calculator(exoMediaDrmProvider)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(exoMediaDrmProvider)

    @Test
    fun invokeOnUnlicensedPlaybackInfo() {
        val playbackInfo = mock<PlaybackInfo.Track>()

        val actual = versionedCdmCalculator(playbackInfo)

        verify(playbackInfo).licenseSecurityToken
        assertThat(actual.cdm).isSameAs(PlaybackStatistics.Payload.Cdm.NONE)
        assertThat(actual.version).isNull()
        verifyNoMoreInteractions(playbackInfo)
    }

    @Test
    fun invokeOnLicensedPlaybackInfo() {
        val playbackInfo =
            mock<PlaybackInfo.Track> { on { licenseSecurityToken } doReturn "licenseSecurityToken" }
        val expectedVersion = "expectedVersion"
        val exoMediaDrm =
            mock<ExoMediaDrm> {
                on { getPropertyString(MediaDrm.PROPERTY_VERSION) } doReturn expectedVersion
            }
        whenever(exoMediaDrmProvider.acquireExoMediaDrm(C.WIDEVINE_UUID)) doReturn exoMediaDrm

        val actual = versionedCdmCalculator(playbackInfo)

        verify(playbackInfo).licenseSecurityToken
        verify(exoMediaDrmProvider).acquireExoMediaDrm(C.WIDEVINE_UUID)
        inOrder(exoMediaDrm).apply {
            verify(exoMediaDrm).getPropertyString(MediaDrm.PROPERTY_VERSION)
            verify(exoMediaDrm).release()
        }
        assertThat(actual.cdm).isSameAs(PlaybackStatistics.Payload.Cdm.WIDEVINE)
        assertThat(actual.version).isEqualTo(expectedVersion)
        verifyNoMoreInteractions(playbackInfo, exoMediaDrm)
    }
}
