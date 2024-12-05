package com.tidal.sdk.flo.core.internal

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.rawType

internal sealed class Command(val type: Type, val topic: String) {

    @JsonClass(generateAdapter = true)
    class Subscribe(topic: String, val data: Data?) : Command(Type.SUBSCRIBE, topic) {

        sealed interface Data {

            @JsonClass(generateAdapter = true)
            class Tail(val tail: Int) : Data

            @JsonClass(generateAdapter = true)
            class LastId(val lastId: String) : Data

            private class Adapter(private val moshi: Moshi) : JsonAdapter<Data>() {

                override fun fromJson(reader: JsonReader) =
                    throw UnsupportedOperationException("Unmarshalling this type is unsupported")

                override fun toJson(writer: JsonWriter, value: Data?) {
                    if (value == null) {
                        writer.nullValue()
                        return
                    }
                    writer.value(
                        when (value) {
                            is Tail -> moshi.adapter(Tail::class.java).toJson(value)
                            is LastId -> moshi.adapter(LastId::class.java).toJson(value)
                        },
                    )
                }
            }

            class JsonAdapterFactory : JsonAdapter.Factory {

                override fun create(
                    type: java.lang.reflect.Type,
                    annotations: MutableSet<out Annotation>,
                    moshi: Moshi,
                ): JsonAdapter<*>? {
                    if (type.rawType != Data::class.java) {
                        return null
                    }
                    return Adapter(moshi)
                }
            }
        }
    }

    @JsonClass(generateAdapter = true)
    class Unsubscribe(topic: String) : Command(Type.UNSUBSCRIBE, topic)

    @JsonClass(generateAdapter = false)
    enum class Type {

        SUBSCRIBE,
        UNSUBSCRIBE,
    }

    class JsonAdapterFactory : JsonAdapter.Factory {

        override fun create(
            type: java.lang.reflect.Type,
            annotations: MutableSet<out Annotation>,
            moshi: Moshi,
        ): JsonAdapter<*>? {
            if (type.rawType != Command::class.java) {
                return null
            }
            return PolymorphicJsonAdapterFactory.of(Command::class.java, KEY_TYPE)
                .withSubtype(Subscribe::class.java, VALUE_TYPE_SUBSCRIBE)
                .withSubtype(Unsubscribe::class.java, VALUE_TYPE_UNSUBSCRIBE)
                .create(type, annotations, moshi)
        }
    }

    companion object {
        private const val KEY_TYPE = "type"
        private const val VALUE_TYPE_SUBSCRIBE = "SUBSCRIBE"
        private const val VALUE_TYPE_UNSUBSCRIBE = "UNSUBSCRIBE"
    }
}
