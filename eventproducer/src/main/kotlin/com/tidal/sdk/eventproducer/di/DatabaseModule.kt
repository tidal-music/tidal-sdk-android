package com.tidal.sdk.eventproducer.di

import android.content.Context
import androidx.room.Room
import com.tidal.sdk.eventproducer.database.EventsDatabase
import com.tidal.sdk.eventproducer.database.EventsDatabase.Companion.EVENTS_DATABASE_NAME
import com.tidal.sdk.eventproducer.events.EventsLocalDataSource
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import com.tidal.sdk.eventproducer.monitoring.MonitoringLocalDataSource
import com.tidal.sdk.eventproducer.utils.DatabaseSizeChecker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideEventsDatabase(context: Context): EventsDatabase = Room.databaseBuilder(
        context,
        EventsDatabase::class.java,
        EVENTS_DATABASE_NAME,
    ).build()

    @Provides
    @Singleton
    fun provideEventLocalDataSource(db: EventsDatabase): EventsLocalDataSource = db.eventDao()

    @Provides
    @Singleton
    fun provideMonitoringDataSource(db: EventsDatabase): MonitoringLocalDataSource {
        return db.monitoringDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseSizeChecker(
        db: EventsDatabase,
        configProvider: EventsConfigProvider,
    ): DatabaseSizeChecker = DatabaseSizeChecker(db, configProvider.config.maxDiskUsageBytes)
}
