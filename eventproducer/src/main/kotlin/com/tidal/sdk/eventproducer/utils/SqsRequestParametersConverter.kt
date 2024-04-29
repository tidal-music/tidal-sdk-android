package com.tidal.sdk.eventproducer.utils

import com.tidal.sdk.eventproducer.model.Event
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SqsRequestParametersConverter @Inject constructor(private val mapConverter: MapConverter) {

    fun getSendEventsParameters(events: List<Event>): Map<String, String> {
        val params = mutableMapOf<String, String>()
        events.forEachIndexed { index, event ->
            val eventIndex = index + 1
            var attributeIndex = 1
            params["$SEND_BATCH.$eventIndex.$ID"] = event.id
            params["$SEND_BATCH.$eventIndex.$BODY"] = event.payload
            params["$SEND_BATCH.$eventIndex.$ATTRIBUTE.$attributeIndex.$NAME_KEY"] = NAME
            params["$SEND_BATCH.$eventIndex.$ATTRIBUTE.$attributeIndex.$VALUE"] = event.name
            params["$SEND_BATCH.$eventIndex.$ATTRIBUTE.$attributeIndex.$VALUE_DATATYPE"] = STRING

            attributeIndex++
            val headersJson = mapConverter.fromStringStringMap(event.headers)
            params["$SEND_BATCH.$eventIndex.$ATTRIBUTE.$attributeIndex.$NAME_KEY"] = HEADERS_KEY
            params["$SEND_BATCH.$eventIndex.$ATTRIBUTE.$attributeIndex.$VALUE_DATATYPE"] =
                STRING
            params["$SEND_BATCH.$eventIndex.$ATTRIBUTE.$attributeIndex.$VALUE"] = headersJson
        }
        return params
    }

    companion object {
        private const val SEND_BATCH = "SendMessageBatchRequestEntry"
        private const val NAME = "Name"
        private const val STRING = "String"
        private const val ATTRIBUTE = "MessageAttribute"
        private const val BODY = "MessageBody"
        private const val ID = "Id"
        private const val NAME_KEY = "Name"
        private const val HEADERS_KEY = "Headers"
        private const val VALUE = "Value.StringValue"
        private const val VALUE_DATATYPE = "Value.DataType"
    }
}
