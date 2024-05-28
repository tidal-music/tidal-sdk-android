package com.tidal.sdk.eventproducer.monitoring

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal abstract class MonitoringDao : MonitoringLocalDataSource {

    override fun insert(monitoringInfo: MonitoringInfo) {
        insertMonitoringEntity(monitoringInfo.toMonitoringEntity())
    }

    override fun getMonitoringInfo(): MonitoringInfo {
        return getMonitoringEntity()?.toMonitoringInfo() ?: MonitoringInfo()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertMonitoringEntity(monitoringEntity: MonitoringEntity)

    @Query("SELECT * FROM monitoring LIMIT 1")
    protected abstract fun getMonitoringEntity(): MonitoringEntity?
}
