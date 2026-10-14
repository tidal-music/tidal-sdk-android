package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** oneOf unions discriminated on a property other than the global `type` class discriminator. */
class CustomDiscriminatorSerializationTest {

    private val json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true
        serializersModule = getOneOfSerializer()
    }

    @Test
    fun `lyrics provider decodes by source and keeps source on encode`() {
        val tidal =
            json.decodeFromString<LyricsAttributes>(
                """{"technicalStatus":"OK","provider":{"source":"TIDAL","name":"TIDAL"}}"""
            )
        val thirdParty =
            json.decodeFromString<LyricsAttributes>(
                """{"technicalStatus":"OK","provider":{"source":"THIRD_PARTY","name":"Musixmatch","lyricsId":"1","commonTrackId":"2"}}"""
            )

        assertEquals(
            TidalLyricsProvider(source = TidalLyricsProvider.Source.TIDAL, name = "TIDAL"),
            tidal.provider,
        )
        assertEquals(
            ThirdPartyLyricsProvider(
                commonTrackId = "2",
                lyricsId = "1",
                name = "Musixmatch",
                source = ThirdPartyLyricsProvider.Source.THIRD_PARTY,
            ),
            thirdParty.provider,
        )
        val encoded =
            json.parseToJsonElement(json.encodeToString(LyricsAttributes.serializer(), thirdParty))
        assertEquals(
            "THIRD_PARTY",
            encoded.jsonObject["provider"]!!.jsonObject["source"]!!.jsonPrimitive.content,
        )
    }

    @Test
    fun `user feed activity details decode by activityType`() {
        val attributes =
            json.decodeFromString<UserFeedItemsAttributes>(
                """{"activityDetails":{"activityType":"PLAYLIST_UPDATED","newItemCount":3},"countIsPeople":false,"eventCount":1,"firstAt":"a","lastAt":"b","surface":"FEED","unread":true}"""
            )

        assertEquals(
            UserFeedItemPlaylistUpdatedDetails(
                activityType = UserFeedItemPlaylistUpdatedDetails.ActivityType.PLAYLIST_UPDATED,
                newItemCount = 3,
            ),
            attributes.activityDetails,
        )
    }
}
