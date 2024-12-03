package com.tidal.sdk.flo.core.internal

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.rawType
import java.lang.reflect.Type

internal sealed class Event(val topic: String) {

    class Message(topic: String, val data: Data) : Event(topic) {

        @JsonClass(generateAdapter = true)
        class Data(
            val payload: String,
            val id: String?,
        )

        private class Adapter(private val moshi: Moshi) : JsonAdapter<Message>() {

            override fun fromJson(reader: JsonReader): Message {
                reader.beginObject()
                var name: String? = null
                var topic: String? = null
                var data: Data? = null
                while (reader.hasNext()) {
                    when (val key = reader.nextName()) {
                        KEY_NAME -> name = reader.nextString()
                        KEY_TOPIC -> topic = reader.nextString()
                        KEY_DATA ->
                            data = moshi.adapter(Data::class.java).fromJson(reader.nextString())

                        else -> throw JsonDataException("Unexpected key: \"$key\"")
                    }
                }
                reader.endObject()
                if (!VALUE_NAME_MESSAGE.contentEquals(name)) {
                    throw JsonDataException("Invalid name: \"$name\"")
                }
                if (topic == null) {
                    throw JsonDataException("Missing key: \"$KEY_TOPIC\"")
                }
                if (data == null) {
                    throw JsonDataException("Missing key: \"$KEY_DATA\"")
                }
                return Message(topic, data)
            }

            override fun toJson(writer: JsonWriter, value: Message?) =
                throw UnsupportedOperationException("Marshalling this type is unsupported")
        }

        class JsonAdapterFactory : JsonAdapter.Factory {

            override fun create(
                type: Type,
                annotations: MutableSet<out Annotation>,
                moshi: Moshi,
            ): JsonAdapter<*>? {
                if (type.rawType != Message::class.java) {
                    return null
                }
                return Adapter(moshi)
            }
        }
    }

    @JsonClass(generateAdapter = true)
    class SubscribeSuccess(topic: String) : Event(topic)

    @JsonClass(generateAdapter = true)
    class UnsubscribeSuccess(topic: String) : Event(topic)

    class JsonAdapterFactory : JsonAdapter.Factory {

        override fun create(
            type: Type,
            annotations: MutableSet<out Annotation>,
            moshi: Moshi,
        ): JsonAdapter<*>? {
            if (type.rawType != Event::class.java) {
                return null
            }
            return PolymorphicJsonAdapterFactory.of(Event::class.java, KEY_NAME)
                .withSubtype(Message::class.java, VALUE_NAME_MESSAGE)
                .withSubtype(SubscribeSuccess::class.java, VALUE_NAME_SUBSCRIBE_SUCCESS)
                .withSubtype(UnsubscribeSuccess::class.java, VALUE_NAME_UNSUBSCRIBE_SUCCESS)
                .create(type, annotations, moshi)
        }
    }

    companion object {
        private const val KEY_NAME = "name"
        private const val KEY_TOPIC = "topic"
        private const val KEY_DATA = "data"
        private const val VALUE_NAME_MESSAGE = "MESSAGE"
        private const val VALUE_NAME_SUBSCRIBE_SUCCESS = "SUBSCRIBE_SUCCESS"
        private const val VALUE_NAME_UNSUBSCRIBE_SUCCESS = "UNSUBSCRIBE_SUCCESS"
    }
}
