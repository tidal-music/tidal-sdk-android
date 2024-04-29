package com.tidal.sdk.eventproducer.monitoring

interface MonitoringLocalDataSource {

    fun insert(monitoringEntity: MonitoringInfo)

    fun getMonitoringInfo(): MonitoringInfo
}
