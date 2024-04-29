package com.tidal.sdk.eventproducer.network.service

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "SendMessageBatchResponse")
data class SendMessageBatchResponse(

    @Element
    val result: SendMessageBatchResult,
)

@Xml(name = "SendMessageBatchResult")
data class SendMessageBatchResult(
    @Element
    val successfullySentEntries: List<SendMessageBatchResultEntry>?,
)

@Xml(name = "SendMessageBatchResultEntry")
data class SendMessageBatchResultEntry(
    @PropertyElement(name = "Id")
    val id: String,
)
