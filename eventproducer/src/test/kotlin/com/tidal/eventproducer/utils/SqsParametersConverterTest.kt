package com.tidal.eventproducer.utils

import com.tidal.sdk.eventproducer.model.Event
import com.tidal.sdk.eventproducer.utils.APP_VERSION_KEY
import com.tidal.sdk.eventproducer.utils.MapConverter
import com.tidal.sdk.eventproducer.utils.OS_NAME_KEY
import com.tidal.sdk.eventproducer.utils.SqsRequestParametersConverter
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

class SqsParametersConverterTest {

    private var mapConverter = mockk<MapConverter>()

    private val sqsParametersConverter = SqsRequestParametersConverter(mapConverter)

    @Test
    fun `Properly converts list of events to sqs parameters`() {
        val firstEventId = "1234"
        val firstEventName = "Event1"
        val osHeaderKey = OS_NAME_KEY
        val androidHeaderValue = "Android"
        val firstEventHeaders = mapOf(osHeaderKey to androidHeaderValue)
        val firstEventPayload = "payload1"
        val secondEventId = "5678"
        val secondEventName = "Event2"
        val iosHeaderValue = "ios"
        val appVersionHeaderKey = APP_VERSION_KEY
        val appVersionHeaderValue = "1.0"
        val secondEventHeaders =
            mapOf(
                osHeaderKey to iosHeaderValue,
                appVersionHeaderKey to appVersionHeaderValue.toString(),
            )
        val payload2 = "payload2"
        val event1 = Event(firstEventId, firstEventName, firstEventHeaders, firstEventPayload)
        val event2 = Event(secondEventId, secondEventName, secondEventHeaders, payload2)

        val firstEventHeadersJson = firstEventHeaders.toString()
        val secondEventHeadersJson = secondEventHeaders.toString()
        every { mapConverter.fromStringStringMap(firstEventHeaders) } returns
            firstEventHeadersJson
        every { mapConverter.fromStringStringMap(secondEventHeaders) } returns
            secondEventHeadersJson

        val expectedParametersStrings = mapOf(
            "SendMessageBatchRequestEntry.1.Id" to firstEventId,
            "SendMessageBatchRequestEntry.1.MessageBody" to firstEventPayload,
            "SendMessageBatchRequestEntry.1.MessageAttribute.1.Name" to "Name",
            "SendMessageBatchRequestEntry.1.MessageAttribute.1.Value.StringValue" to firstEventName,
            "SendMessageBatchRequestEntry.1.MessageAttribute.1.Value.DataType" to "String",
            "SendMessageBatchRequestEntry.1.MessageAttribute.2.Name" to "Headers",
            "SendMessageBatchRequestEntry.1.MessageAttribute.2.Value.DataType" to "String",
            "SendMessageBatchRequestEntry.1.MessageAttribute.2.Value.StringValue" to
                firstEventHeadersJson,
            "SendMessageBatchRequestEntry.2.Id" to secondEventId,
            "SendMessageBatchRequestEntry.2.MessageBody" to payload2,
            "SendMessageBatchRequestEntry.2.MessageAttribute.1.Name" to "Name",
            "SendMessageBatchRequestEntry.2.MessageAttribute.1.Value.StringValue" to
                secondEventName,
            "SendMessageBatchRequestEntry.2.MessageAttribute.1.Value.DataType" to "String",
            "SendMessageBatchRequestEntry.2.MessageAttribute.2.Name" to "Headers",
            "SendMessageBatchRequestEntry.2.MessageAttribute.2.Value.DataType" to "String",
            "SendMessageBatchRequestEntry.2.MessageAttribute.2.Value.StringValue" to
                secondEventHeadersJson,
        )

        val convertedParameters = sqsParametersConverter.getSendEventsParameters(
            listOf(
                event1,
                event2,
            ),
        )
        assertEquals(expectedParametersStrings, convertedParameters)
    }
}
