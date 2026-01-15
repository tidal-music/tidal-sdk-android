package com.tidal.sdk.eventproducer.network.service

import assertk.assertThat
import assertk.assertions.*
import kotlinx.serialization.ExperimentalSerializationApi
import nl.adaptivity.xmlutil.serialization.UnknownChildHandler
import nl.adaptivity.xmlutil.serialization.XML
import org.junit.jupiter.api.Test

/**
 * Tests to ensure XML parsing behavior is identical to the original TikXML implementation. These
 * tests verify that we can correctly parse AWS SQS SendMessageBatch responses.
 */
class SendMessageBatchResponseXmlTest {

    @OptIn(ExperimentalSerializationApi::class)
    private val xml = XML {
        autoPolymorphic = false
        unknownChildHandler = UnknownChildHandler { _, _, _, _, _ -> emptyList() }
    }

    @Test
    fun `should parse successful SQS response with multiple entries`() {
        // Given - typical successful AWS SQS response
        val xmlResponse =
            """
            <SendMessageBatchResponse>
                <SendMessageBatchResult>
                    <SendMessageBatchResultEntry>
                        <Id>message_1</Id>
                        <MessageId>0a5231c7-8bff-4955-be2e-8dc7c50a25fa</MessageId>
                        <MD5OfBody>0e024d309850c78cba5eabbeff7cae71</MD5OfBody>
                    </SendMessageBatchResultEntry>
                    <SendMessageBatchResultEntry>
                        <Id>message_2</Id>
                        <MessageId>1b6342e8-9c00-5a66-cf3f-9ed8d60b36gb</MessageId>
                        <MD5OfBody>1f135e3a195b729e81c8b7b7f9e8a8f2</MD5OfBody>
                    </SendMessageBatchResultEntry>
                </SendMessageBatchResult>
                <ResponseMetadata>
                    <RequestId>ca1ad5d0-8271-408b-8d0f-1351bf547e74</RequestId>
                </ResponseMetadata>
            </SendMessageBatchResponse>
        """
                .trimIndent()

        // When
        val response = xml.decodeFromString(SendMessageBatchResponse.serializer(), xmlResponse)

        // Then - verify correct parsing
        assertThat(response.result).isNotNull()
        assertThat(response.result.successfullySentEntries).isNotNull()
        assertThat(response.result.successfullySentEntries!!).hasSize(2)
        assertThat(response.result.successfullySentEntries!![0].id).isEqualTo("message_1")
        assertThat(response.result.successfullySentEntries!![1].id).isEqualTo("message_2")
    }

    @Test
    fun `should parse SQS response with no successful entries`() {
        // Given - AWS SQS response with no successful entries (all failed)
        val xmlResponse =
            """
            <SendMessageBatchResponse>
                <SendMessageBatchResult>
                </SendMessageBatchResult>
                <ResponseMetadata>
                    <RequestId>ca1ad5d0-8271-408b-8d0f-1351bf547e74</RequestId>
                </ResponseMetadata>
            </SendMessageBatchResponse>
        """
                .trimIndent()

        // When
        val response = xml.decodeFromString(SendMessageBatchResponse.serializer(), xmlResponse)

        // Then - result should be present but entries should be null or empty
        assertThat(response.result).isNotNull()
        // This tests the original TikXML behavior where missing elements were null
        assertThat(response.result.successfullySentEntries).isNull()
    }

    @Test
    fun `should parse minimal SQS response with one entry`() {
        // Given - minimal valid response
        val xmlResponse =
            """
            <SendMessageBatchResponse>
                <SendMessageBatchResult>
                    <SendMessageBatchResultEntry>
                        <Id>single_message</Id>
                    </SendMessageBatchResultEntry>
                </SendMessageBatchResult>
            </SendMessageBatchResponse>
        """
                .trimIndent()

        // When
        val response = xml.decodeFromString(SendMessageBatchResponse.serializer(), xmlResponse)

        // Then
        assertThat(response.result).isNotNull()
        assertThat(response.result.successfullySentEntries).isNotNull()
        assertThat(response.result.successfullySentEntries!!).hasSize(1)
        assertThat(response.result.successfullySentEntries!![0].id).isEqualTo("single_message")
    }

    @Test
    fun `should handle unknown XML elements gracefully`() {
        // Given - response with additional unknown elements
        val xmlResponse =
            """
            <SendMessageBatchResponse>
                <SendMessageBatchResult>
                    <SendMessageBatchResultEntry>
                        <Id>message_1</Id>
                        <UnknownField>some_value</UnknownField>
                    </SendMessageBatchResultEntry>
                    <AnotherUnknownElement>data</AnotherUnknownElement>
                </SendMessageBatchResult>
                <UnexpectedElement>should_be_ignored</UnexpectedElement>
            </SendMessageBatchResponse>
        """
                .trimIndent()

        // When/Then - should not throw exception (equivalent to exceptionOnUnreadXml(false))
        val response = xml.decodeFromString(SendMessageBatchResponse.serializer(), xmlResponse)

        assertThat(response.result).isNotNull()
        assertThat(response.result.successfullySentEntries).isNotNull()
        assertThat(response.result.successfullySentEntries!!).hasSize(1)
        assertThat(response.result.successfullySentEntries!![0].id).isEqualTo("message_1")
    }

    @Test
    fun `should construct objects programmatically matching original TikXML behavior`() {
        // Test the programmatic construction patterns used in the existing tests

        // Test with null entries (original behavior)
        val responseWithNullEntries = SendMessageBatchResponse(SendMessageBatchResult(null))
        assertThat(responseWithNullEntries.result.successfullySentEntries).isNull()

        // Test with empty entries list
        val responseWithEmptyEntries = SendMessageBatchResponse(SendMessageBatchResult(emptyList()))
        assertThat(responseWithEmptyEntries.result.successfullySentEntries).isNotNull()
        assertThat(responseWithEmptyEntries.result.successfullySentEntries!!).isEmpty()

        // Test with actual entries
        val responseWithEntries =
            SendMessageBatchResponse(
                SendMessageBatchResult(
                    listOf(SendMessageBatchResultEntry("msg1"), SendMessageBatchResultEntry("msg2"))
                )
            )
        assertThat(responseWithEntries.result.successfullySentEntries).isNotNull()
        assertThat(responseWithEntries.result.successfullySentEntries!!).hasSize(2)
    }

    @Test
    fun `serialization should produce valid AWS SQS format`() {
        // Given
        val response =
            SendMessageBatchResponse(
                SendMessageBatchResult(listOf(SendMessageBatchResultEntry("test_message_1")))
            )

        // When
        val xmlOutput = xml.encodeToString(SendMessageBatchResponse.serializer(), response)

        // Then - verify it contains the expected SQS structure
        assertThat(xmlOutput).contains("<SendMessageBatchResponse>")
        assertThat(xmlOutput).contains("<SendMessageBatchResult>")
        assertThat(xmlOutput).contains("<SendMessageBatchResultEntry>")
        assertThat(xmlOutput).contains("<Id>test_message_1</Id>")
        assertThat(xmlOutput).contains("</SendMessageBatchResultEntry>")
        assertThat(xmlOutput).contains("</SendMessageBatchResult>")
        assertThat(xmlOutput).contains("</SendMessageBatchResponse>")
    }
}
