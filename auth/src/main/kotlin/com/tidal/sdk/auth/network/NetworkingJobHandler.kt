package com.tidal.sdk.auth.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NetworkingJobHandler(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    val scope = CoroutineScope(dispatcher)

    val jobs = mutableListOf<Job>()

    fun cancelAllJobs() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}
