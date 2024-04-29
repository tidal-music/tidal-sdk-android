package com.tidal.sdk.eventproducer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tidal.sdk.eventproducer.events.EventDao
import com.tidal.sdk.eventproducer.events.EventEntity
import com.tidal.sdk.eventproducer.monitoring.MonitoringDao
import com.tidal.sdk.eventproducer.monitoring.MonitoringEntity
import com.tidal.sdk.eventproducer.utils.MapConverter
import java.io.File

@TypeConverters(MapConverter::class)
@Database(entities = [EventEntity::class, MonitoringEntity::class], version = 1)
abstract class EventsDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun monitoringDao(): MonitoringDao

    fun isDatabaseLimitReached(maxDiskUsageBytes: Int): Boolean {
        val dbPath = openHelper.writableDatabase.path
        val dbSize = dbPath?.let { path ->
            val mainDbFileSize = File(path).length()
            val shmFileSize = File("$path-shm").length()
            val walFileSize = File("$path-wal").length()
            val journalFileSize = File("$path-journal").length()
            mainDbFileSize + shmFileSize + walFileSize + journalFileSize
        }
        return dbSize?.let { dbSize >= maxDiskUsageBytes } ?: true
    }

    companion object {
        const val EVENTS_DATABASE_NAME = "events_database"
    }
}
