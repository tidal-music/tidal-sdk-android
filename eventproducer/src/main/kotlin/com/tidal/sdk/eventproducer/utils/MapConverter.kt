package com.tidal.sdk.eventproducer.utils

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Reusable
import javax.inject.Inject

@Reusable
internal class MapConverter @Inject constructor() {

    private val mapStringStringType = Types.newParameterizedType(
        Map::class.java,
        String::class.java,
        String::class.java,
    )

    private val mapStringIntType = Types.newParameterizedType(
        Map::class.java,
        String::class.java,
        Int::class.javaObjectType,
    )

    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun toStringStringMap(value: String): Map<String, String>? {
        return moshi.adapter<Map<String, String>>(mapStringStringType).fromJson(value)
    }

    @TypeConverter
    fun fromStringStringMap(map: Map<String, String>): String {
        return moshi.adapter<Map<String, String>>(mapStringStringType).toJson(map)
    }

    @TypeConverter
    fun toIntStringMap(value: String): Map<String, Int>? {
        return moshi.adapter<Map<String, Int>>(mapStringIntType).fromJson(value)
    }

    @TypeConverter
    fun fromIntStringMap(map: Map<String, Int>): String {
        return moshi.adapter<Map<String, Int>>(mapStringIntType).toJson(map)
    }
}
