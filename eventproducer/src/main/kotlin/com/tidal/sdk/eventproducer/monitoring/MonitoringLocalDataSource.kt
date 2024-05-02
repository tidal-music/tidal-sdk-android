package com.tidal.sdk.eventproducer.monitoring

internal interface MonitoringLocalDataSource {

    fun insert(monitoringEntity: MonitoringInfo)

    fun getMonitoringInfo(): MonitoringInfo
}
