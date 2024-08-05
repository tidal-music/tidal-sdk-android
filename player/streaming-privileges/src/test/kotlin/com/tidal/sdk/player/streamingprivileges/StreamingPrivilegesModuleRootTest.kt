package com.tidal.sdk.player.streamingprivileges

import android.net.ConnectivityManager
import assertk.assertThat
import assertk.assertions.isSameAs
import com.google.gson.Gson
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.streamingprivileges.di.StreamingPrivilegesComponent
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

internal class StreamingPrivilegesModuleRootTest {

    private val connectivityManager = mock<ConnectivityManager>()
    private val okHttpClient = mock<OkHttpClient>()
    private val gson = mock<Gson>()
    private val sntpClient = mock<SNTPClient>()
    private val streamingPrivilegesModuleRoot by lazy {
        StreamingPrivilegesModuleRoot(
            connectivityManager,
            okHttpClient,
            gson,
            sntpClient,
        )
    }

    companion object {
        private lateinit var originalComponentFactoryF: () -> StreamingPrivilegesComponent.Factory

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            originalComponentFactoryF = StreamingPrivilegesModuleRoot.reflectionComponentFactoryF
        }
    }

    @AfterEach
    fun afterEach() {
        StreamingPrivilegesModuleRoot.reflectionComponentFactoryF = originalComponentFactoryF
        verifyNoMoreInteractions(connectivityManager, okHttpClient, gson)
    }

    @Test
    fun propertyReturnsStreamingPrivilegesFromComponent() {
        val expected = mock<StreamingPrivileges>()
        val component = mock<StreamingPrivilegesComponent> {
            on { streamingPrivileges } doReturn expected
        }
        val componentFactory = mock<StreamingPrivilegesComponent.Factory> {
            on {
                create(
                    connectivityManager,
                    okHttpClient,
                    gson,
                    sntpClient,
                )
            } doReturn component
        }
        StreamingPrivilegesModuleRoot.reflectionComponentFactoryF = { componentFactory }

        val actual = streamingPrivilegesModuleRoot.streamingPrivileges

        verify(componentFactory).create(
            connectivityManager,
            okHttpClient,
            gson,
            sntpClient,
        )
        verify(component).streamingPrivileges
        verifyNoMoreInteractions(expected, component, componentFactory)
        assertThat(actual).isSameAs(expected)
    }
}
