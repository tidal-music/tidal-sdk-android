package com.tidal.sdk.player.events

import android.content.Context
import android.net.ConnectivityManager
import assertk.assertThat
import assertk.assertions.isSameAs
import com.google.gson.Gson
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.di.DefaultEventReporterComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

internal class EventReporterModuleRootTest {

    private val context = mock<Context>()
    private val connectivityManager = mock<ConnectivityManager>()
    private val userSupplier = mock<UserSupplier>()
    private val clientSupplier = mock<ClientSupplier>()
    private val okHttpClient = mock<OkHttpClient>()
    private val gson = mock<Gson>()
    private val uuidWrapper = mock<UUIDWrapper>()
    private val trueTimeWrapper = mock<TrueTimeWrapper>()
    private val eventSender = mock<EventSender>()
    private val configuration = mock<Configuration>()
    private val coroutineScope = mock<CoroutineScope>()
    private val eventReporterModuleRoot by lazy {
        EventReporterModuleRoot(
            context,
            connectivityManager,
            userSupplier,
            clientSupplier,
            okHttpClient,
            gson,
            uuidWrapper,
            trueTimeWrapper,
            eventSender,
            coroutineScope,
        )
    }

    companion object {
        private lateinit var originalComponentFactoryF: () -> DefaultEventReporterComponent.Factory

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            originalComponentFactoryF = EventReporterModuleRoot.reflectionComponentFactoryF
        }
    }

    @AfterEach
    fun afterEach() {
        EventReporterModuleRoot.reflectionComponentFactoryF = originalComponentFactoryF
        verifyNoMoreInteractions(
            context,
            connectivityManager,
            userSupplier,
            clientSupplier,
            okHttpClient,
            gson,
            uuidWrapper,
            trueTimeWrapper,
            eventSender,
            configuration,
        )
    }

    @Test
    fun propertyReturnsEventReporterFromComponent() {
        val expected = mock<EventReporter>()
        val component =
            mock<DefaultEventReporterComponent> { on { eventReporter } doReturn expected }
        val componentFactory =
            mock<DefaultEventReporterComponent.Factory> {
                on {
                    create(
                        context,
                        connectivityManager,
                        userSupplier,
                        clientSupplier,
                        okHttpClient,
                        gson,
                        uuidWrapper,
                        trueTimeWrapper,
                        eventSender,
                        coroutineScope,
                    )
                } doReturn component
            }
        EventReporterModuleRoot.reflectionComponentFactoryF = { componentFactory }

        val actual = eventReporterModuleRoot.eventReporter

        verify(componentFactory)
            .create(
                context,
                connectivityManager,
                userSupplier,
                clientSupplier,
                okHttpClient,
                gson,
                uuidWrapper,
                trueTimeWrapper,
                eventSender,
                coroutineScope,
            )
        verify(component).eventReporter
        verifyNoMoreInteractions(expected, component, componentFactory)
        assertThat(actual).isSameAs(expected)
    }
}
