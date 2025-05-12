package com.tidal.sdk.player.playbackengine.error

import androidx.media3.datasource.HttpDataSource.HttpDataSourceException
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class ErrorCodeFactoryTest {

    private val errorCodeFactory = ErrorCodeFactory()

    @Test
    fun `createForOther should return correct errorCode`() =
        assertThat(errorCodeFactory.createForOther(ErrorCodeFactory.Extra.DrmLicenseFetch))
            .isEqualTo("-1:1:-1")

    @Test
    fun `createForApiError should return correct errorCode`() =
        assertThat(errorCodeFactory.createForApiError(403, 4010)).isEqualTo("0:403:4010")

    @Test
    fun `createForTimeout should return correct errorCode`() =
        assertThat(errorCodeFactory.createForTimeout(ErrorCodeFactory.Extra.DrmLicenseFetch))
            .isEqualTo("1:1:-1")

    @Test
    fun `createForNetwork should return correct errorCode`() =
        assertThat(errorCodeFactory.createForNetwork(ErrorCodeFactory.Extra.DrmLicenseFetch))
            .isEqualTo("2:1:-1")

    @Test
    fun `createForInvalidResponseCode should return correct errorCode`() =
        assertThat(errorCodeFactory.createForInvalidResponseCode(403, -1)).isEqualTo("3:403:-1")

    @Test
    fun `createForHttpDataSource should return correct errorCode`() =
        assertThat(errorCodeFactory.createForHttpDataSource(HttpDataSourceException.TYPE_OPEN, -1))
            .isEqualTo("4:1:-1")

    @Test
    fun `createForDrmSession should return correct errorCode`() =
        assertThat(errorCodeFactory.createForDrmSession(ErrorCodeFactory.Extra.DrmLicenseFetch, -1))
            .isEqualTo("5:1:-1")

    @Test
    fun `createForUnexpectedLoader should return correct errorCode`() =
        assertThat(
                errorCodeFactory.createForUnexpectedLoader(
                    ErrorCodeFactory.Extra.DrmLicenseFetch,
                    -1,
                )
            )
            .isEqualTo("6:1:-1")

    @Test
    fun `createForBehindLiveWindow should return correct errorCode`() =
        assertThat(
                errorCodeFactory.createForBehindLiveWindow(
                    ErrorCodeFactory.Extra.DrmLicenseFetch,
                    -1,
                )
            )
            .isEqualTo("7:1:-1")

    @Test
    fun `createForAudioSinkInitialization should return correct errorCode`() =
        assertThat(
                errorCodeFactory.createForAudioSinkInitialization(
                    ErrorCodeFactory.Extra.DrmLicenseFetch,
                    -1,
                )
            )
            .isEqualTo("8:1:-1")
}
