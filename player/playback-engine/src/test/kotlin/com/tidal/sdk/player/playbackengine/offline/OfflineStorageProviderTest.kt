package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.DataSource
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.streamingapi.offline.Storage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class OfflineStorageProviderTest {

    private val offlinePlayDataSourceFactoryHelper = mock<OfflinePlayDataSourceFactoryHelper>()
    private val offlinePlayDrmDataSourceFactoryHelper =
        mock<OfflinePlayDrmDataSourceFactoryHelper>()
    private val offlineStorageProvider = OfflineStorageProvider(
        offlinePlayDataSourceFactoryHelper,
        offlinePlayDrmDataSourceFactoryHelper,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        offlinePlayDataSourceFactoryHelper,
        offlinePlayDrmDataSourceFactoryHelper,
    )

    @Test
    fun getDataSourceFactoryForOfflinePlayFromInternalAndNotProtected() {
        val storage = Storage(false, "path")
        val isDrmProtected = false
        val expectedDataSourceFactoryForOfflinePlay = mock<DataSource.Factory>()
        whenever(offlinePlayDataSourceFactoryHelper.getInternal(storage.path))
            .thenReturn(expectedDataSourceFactoryForOfflinePlay)

        val actualDataSourceFactoryForOfflinePlay =
            offlineStorageProvider.getDataSourceFactoryForOfflinePlay(storage, isDrmProtected)

        assertThat(actualDataSourceFactoryForOfflinePlay)
            .isSameAs(expectedDataSourceFactoryForOfflinePlay)
        verify(offlinePlayDataSourceFactoryHelper).getInternal(storage.path)
    }

    @Test
    fun getDataSourceFactoryForOfflinePlayFromExternalAndNotProtected() {
        val storage = Storage(true, "path")
        val isDrmProtected = false
        val expectedDataSourceFactoryForOfflinePlay = mock<DataSource.Factory>()
        whenever(offlinePlayDataSourceFactoryHelper.getExternal(storage.path))
            .thenReturn(expectedDataSourceFactoryForOfflinePlay)

        val actualDataSourceFactoryForOfflinePlay =
            offlineStorageProvider.getDataSourceFactoryForOfflinePlay(storage, isDrmProtected)

        assertThat(actualDataSourceFactoryForOfflinePlay)
            .isSameAs(expectedDataSourceFactoryForOfflinePlay)
        verify(offlinePlayDataSourceFactoryHelper).getExternal(storage.path)
    }

    @Test
    fun getDataSourceFactoryForOfflinePlayFromInternalAndDrmProtected() {
        val storage = Storage(false, "path")
        val isDrmProtected = true
        val expectedDataSourceFactoryForOfflinePlay = mock<DataSource.Factory>()
        whenever(offlinePlayDrmDataSourceFactoryHelper.getInternal(storage.path))
            .thenReturn(expectedDataSourceFactoryForOfflinePlay)

        val actualDataSourceFactoryForOfflinePlay =
            offlineStorageProvider.getDataSourceFactoryForOfflinePlay(storage, isDrmProtected)

        assertThat(actualDataSourceFactoryForOfflinePlay)
            .isSameAs(expectedDataSourceFactoryForOfflinePlay)
        verify(offlinePlayDrmDataSourceFactoryHelper).getInternal(storage.path)
    }

    @Test
    fun getDataSourceFactoryForOfflinePlayFromExternalAndDrmProtected() {
        val storage = Storage(true, "path")
        val isDrmProtected = true
        val expectedDataSourceFactoryForOfflinePlay = mock<DataSource.Factory>()
        whenever(offlinePlayDrmDataSourceFactoryHelper.getExternal(storage.path))
            .thenReturn(expectedDataSourceFactoryForOfflinePlay)

        val actualDataSourceFactoryForOfflinePlay =
            offlineStorageProvider.getDataSourceFactoryForOfflinePlay(storage, isDrmProtected)

        assertThat(actualDataSourceFactoryForOfflinePlay)
            .isSameAs(expectedDataSourceFactoryForOfflinePlay)
        verify(offlinePlayDrmDataSourceFactoryHelper).getExternal(storage.path)
    }
}
