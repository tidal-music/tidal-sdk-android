package com.tidal.eventproducer.repository

import android.database.sqlite.SQLiteException
import com.tidal.sdk.eventproducer.EventProducer
import com.tidal.sdk.eventproducer.events.EventsLocalDataSource
import com.tidal.sdk.eventproducer.model.Event
import com.tidal.sdk.eventproducer.model.MonitoringEvent
import com.tidal.sdk.eventproducer.model.MonitoringEventType
import com.tidal.sdk.eventproducer.monitoring.MonitoringInfo
import com.tidal.sdk.eventproducer.monitoring.MonitoringLocalDataSource
import com.tidal.sdk.eventproducer.network.service.SendMessageBatchResponse
import com.tidal.sdk.eventproducer.network.service.SendMessageBatchResult
import com.tidal.sdk.eventproducer.network.service.SqsService
import com.tidal.sdk.eventproducer.repository.EventsRepository
import com.tidal.sdk.eventproducer.repository.RepositoryHelper
import com.tidal.sdk.eventproducer.utils.SqsRequestParametersConverter
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventsRepositoryTest {

    @MockK private lateinit var eventsLocalDataSource: EventsLocalDataSource

    @MockK private lateinit var monitoringDataSource: MonitoringLocalDataSource

    @MockK private lateinit var repositoryHelper: RepositoryHelper

    @MockK private lateinit var sqsParametersConverter: SqsRequestParametersConverter

    @MockK private lateinit var sqsService: SqsService

    private lateinit var eventsRepository: EventsRepository

    @BeforeEach
    fun init() {
        clearAllMocks()
        MockKAnnotations.init(this)
        eventsRepository =
            EventsRepository(
                eventsLocalDataSource,
                monitoringDataSource,
                repositoryHelper,
                sqsParametersConverter,
                sqsService,
            )
    }

    @Test
    fun `insert event add event to local data source when database size limit has not been reached`() {
        // given
        val event = Event("1", "name", emptyMap(), "payload")
        every { repositoryHelper.isDatabaseLimitReached() } returns false
        every { eventsLocalDataSource.insertEvent(any()) } returns Unit

        // when
        eventsRepository.insertEvent(event)

        // then
        verify { eventsLocalDataSource.insertEvent(event) }
    }

    @Test
    fun `insert event updates storing failed monitoring data when database size limit has been reached`() {
        // given
        val eventName = "eventName"
        val event = Event("1", eventName, emptyMap(), "payload")
        val monitoringInfoBeforeUpdate =
            MonitoringInfo(mutableMapOf(), mutableMapOf(), mutableMapOf())
        val monitoringInfoAfterUpdate =
            MonitoringInfo(
                mutableMapOf(),
                mutableMapOf(),
                storingFailedEvents = mutableMapOf(eventName to 1),
            )
        every { repositoryHelper.isDatabaseLimitReached() } returns true
        every { monitoringDataSource.getMonitoringInfo() } returns monitoringInfoBeforeUpdate
        every { monitoringDataSource.insert(any()) } returns Unit

        // when
        eventsRepository.insertEvent(event)

        // then
        verify { monitoringDataSource.insert(monitoringInfoAfterUpdate) }
    }

    @Test
    fun `insert event starts outage and updates monitoring data when data source responses with exception`() {
        // given
        val eventName = "eventName"
        val event = Event("1", eventName, emptyMap(), "payload")
        val monitoringInfoBeforeUpdate =
            MonitoringInfo(mutableMapOf(), mutableMapOf(), mutableMapOf())
        val monitoringInfoAfterUpdate =
            MonitoringInfo(
                mutableMapOf(),
                mutableMapOf(),
                storingFailedEvents = mutableMapOf(eventName to 1),
            )
        every { repositoryHelper.isDatabaseLimitReached() } returns false
        every { monitoringDataSource.getMonitoringInfo() } returns monitoringInfoBeforeUpdate
        every { monitoringDataSource.insert(any()) } returns Unit
        every { eventsLocalDataSource.insertEvent(any()) } throws SQLiteException()
        mockkObject(EventProducer.Companion)
        every { EventProducer.instance?.startOutage() } returns Unit

        // when
        eventsRepository.insertEvent(event)

        // then
        verify { monitoringDataSource.insert(monitoringInfoAfterUpdate) }
        verify { EventProducer.instance?.startOutage() }

        unmockkObject(EventProducer.Companion)
    }

    @Test
    fun `delete event deletes event from local data source`() {
        // given
        val eventIds = listOf("1", "3", "5")
        every { eventsLocalDataSource.deleteEvents(eventIds) } returns Unit

        // when
        eventsRepository.deleteEvents(eventIds)

        // then
        verify { eventsLocalDataSource.deleteEvents(eventIds) }
    }

    @Test
    fun `get all returns events from local data source`() {
        // given
        every { eventsLocalDataSource.getAllEvents() } returns emptyList()

        // when
        eventsRepository.getAll()

        // then
        verify { eventsLocalDataSource.getAllEvents() }
    }

    @Test
    fun `send events to sqs invokes sendEventBatch on sqsService when user is logged in`() =
        runTest {
            // given
            val sqsParameters = mapOf("SendMessageBatchRequestEntry.1.Id" to "1")
            val response = SendMessageBatchResponse(SendMessageBatchResult(null))
            every { sqsParametersConverter.getSendEventsParameters(any()) } returns sqsParameters
            every { repositoryHelper.isUserLoggedIn() } returns true
            coEvery { sqsService.sendEventsBatch(any()) } returns response

            // when
            eventsRepository.sendEventsToSqs(listOf())

            // then
            coVerify { sqsService.sendEventsBatch(sqsParameters) }
        }

    @Test
    fun `send events to sqs invokes sendEventBatchPublic on sqsService when user is logged out`() =
        runTest {
            // given
            val sqsParameters = mapOf("SendMessageBatchRequestEntry.1.Id" to "1")
            val response = SendMessageBatchResponse(SendMessageBatchResult(null))
            every { sqsParametersConverter.getSendEventsParameters(any()) } returns sqsParameters
            every { repositoryHelper.isUserLoggedIn() } returns false
            coEvery { sqsService.sendEventsBatchPublic(any()) } returns response

            // when
            eventsRepository.sendEventsToSqs(listOf())

            // then
            coVerify { sqsService.sendEventsBatchPublic(sqsParameters) }
        }

    @Test
    fun `send events returns success result with payload returned by service`() = runTest {
        // given
        val sqsParameters = mapOf("SendMessageBatchRequestEntry.1.Id" to "1")
        val serviceResponse = SendMessageBatchResponse(SendMessageBatchResult(null))
        every { sqsParametersConverter.getSendEventsParameters(any()) } returns sqsParameters
        every { repositoryHelper.isUserLoggedIn() } returns false
        coEvery { sqsService.sendEventsBatchPublic(any()) } returns serviceResponse

        // when
        val result = eventsRepository.sendEventsToSqs(listOf())

        // then
        assert(result.isSuccess)
        assert(result.successData == serviceResponse)
    }

    @Test
    fun `send events returns failure result when service throws an exception`() = runTest {
        // given
        val sqsParameters = mapOf("SendMessageBatchRequestEntry.1.Id" to "1")
        every { sqsParametersConverter.getSendEventsParameters(any()) } returns sqsParameters
        every { repositoryHelper.isUserLoggedIn() } returns false
        coEvery { sqsService.sendEventsBatchPublic(any()) } throws TimeoutException()

        // when
        val result = eventsRepository.sendEventsToSqs(listOf())

        // then
        assert(result.isFailure)
    }

    @Test
    fun `increase event validation failed occurrence updates monitoring data in monitoring data source`() {
        // given
        val eventName = "eventName"
        val monitoringInfoBeforeUpdate =
            MonitoringInfo(mutableMapOf(), mutableMapOf(), mutableMapOf())
        val monitoringInfoAfterUpdate =
            MonitoringInfo(
                mutableMapOf(),
                validationFailedEvents = mutableMapOf(eventName to 1),
                mutableMapOf(),
            )
        every { monitoringDataSource.getMonitoringInfo() } returns monitoringInfoBeforeUpdate
        every { monitoringDataSource.insert(any()) } returns Unit

        // when
        eventsRepository.storeNewMonitoringEvent(
            MonitoringEvent(MonitoringEventType.EventValidationFailed, eventName)
        )

        // then
        verify { monitoringDataSource.insert(monitoringInfoAfterUpdate) }
    }

    @Test
    fun `increase event consent filtered occurrence updates monitoring data in monitoring data source`() {
        // given
        val eventName = "eventName"
        val monitoringInfoBeforeUpdate =
            MonitoringInfo(mutableMapOf(), mutableMapOf(), mutableMapOf())
        val monitoringInfoAfterUpdate =
            MonitoringInfo(
                consentFilteredEvents = mutableMapOf(eventName to 1),
                mutableMapOf(),
                mutableMapOf(),
            )
        every { monitoringDataSource.getMonitoringInfo() } returns monitoringInfoBeforeUpdate
        every { monitoringDataSource.insert(any()) } returns Unit

        // when
        eventsRepository.storeNewMonitoringEvent(
            MonitoringEvent(MonitoringEventType.EventConsentFiltered, eventName)
        )

        // then
        verify { monitoringDataSource.insert(monitoringInfoAfterUpdate) }
    }

    @Test
    fun `send monitoring event invokes send event batch on sqsService when monitoring info not empty`() =
        runTest {
            // given
            val monitoringInfo =
                MonitoringInfo(mutableMapOf("event1" to 1), mutableMapOf(), mutableMapOf())
            val monitoringEvent = Event("1", "eventName", emptyMap(), "payload")
            val sqsParameters = mapOf("SendMessageBatchRequestEntry.1.Id" to "1")
            val serviceResponse = SendMessageBatchResponse(SendMessageBatchResult(null))
            every { monitoringDataSource.getMonitoringInfo() } returns monitoringInfo
            every { repositoryHelper.getMonitoringEvent(monitoringInfo) } returns monitoringEvent
            every { repositoryHelper.isUserLoggedIn() } returns true
            every {
                sqsParametersConverter.getSendEventsParameters(listOf(monitoringEvent))
            } returns sqsParameters
            coEvery { sqsService.sendEventsBatch(sqsParameters) } returns serviceResponse

            // when
            eventsRepository.sendMonitoringEvent()

            // then
            coVerify { sqsService.sendEventsBatch(sqsParameters) }
        }

    @Test
    fun `send monitoring event doesn't invoke send event batch on sqsService when monitoring info is empty`() =
        runTest {
            // given
            val monitoringInfo = MonitoringInfo(mutableMapOf(), mutableMapOf(), mutableMapOf())
            every { monitoringDataSource.getMonitoringInfo() } returns monitoringInfo

            // when
            eventsRepository.sendMonitoringEvent()

            // then
            coVerify(exactly = 0) { sqsService.sendEventsBatch(any()) }
        }

    @Test
    fun `clear monitoring info inserts empty object as monitoring info to monitoring data source`() {
        // given
        val emptyMonitoringInfo = MonitoringInfo(mutableMapOf(), mutableMapOf(), mutableMapOf())
        every { monitoringDataSource.insert(any()) } returns Unit

        // when
        eventsRepository.clearMonitoringInfo()

        // then
        verify { monitoringDataSource.insert(emptyMonitoringInfo) }
    }
}
