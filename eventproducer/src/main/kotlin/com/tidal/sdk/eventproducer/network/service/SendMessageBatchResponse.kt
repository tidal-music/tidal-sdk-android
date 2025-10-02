package com.tidal.sdk.eventproducer.network.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("SendMessageBatchResponse")
internal data class SendMessageBatchResponse(
    @XmlElement(true)
    @XmlSerialName("SendMessageBatchResult")
    val result: SendMessageBatchResult
)

@Serializable
@XmlSerialName("SendMessageBatchResult")
internal data class SendMessageBatchResult(
    @XmlElement(true)
    @XmlSerialName("SendMessageBatchResultEntry")
    val successfullySentEntries: List<SendMessageBatchResultEntry>? = null
)

@Serializable
@XmlSerialName("SendMessageBatchResultEntry")
internal data class SendMessageBatchResultEntry(
    @XmlElement(true)
    @XmlSerialName("Id")
    val id: String
)
