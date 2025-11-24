package com.tidal.sdk.eventproducer.network.service

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "SendMessageBatchResponse", strict = false)
internal data class SendMessageBatchResponse(
    @field:Element(name = "SendMessageBatchResult") var result: SendMessageBatchResult? = null
)

@Root(name = "SendMessageBatchResult", strict = false)
internal data class SendMessageBatchResult(
    @field:ElementList(inline = true, entry = "SendMessageBatchResultEntry", required = false)
    var successfullySentEntries: List<SendMessageBatchResultEntry>? = null
)

@Root(name = "SendMessageBatchResultEntry", strict = false)
internal data class SendMessageBatchResultEntry(@field:Element(name = "Id") var id: String = "")
