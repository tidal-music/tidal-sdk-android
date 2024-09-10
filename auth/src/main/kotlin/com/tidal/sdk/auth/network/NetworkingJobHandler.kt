package com.tidal.sdk.auth.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NetworkingJobHandler(val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {

    val jobs = mutableListOf<Job>()

    fun cancelAllJobs() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}
