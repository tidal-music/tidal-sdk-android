package com.tidal.sdk.eventproducer.network.service

import java.util.*
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SqsService {

    @FormUrlEncoded
    @POST("api/event-batch")
    suspend fun sendEventsBatch(
        @FieldMap parameters: Map<String, String>,
    ): SendMessageBatchResponse

    @FormUrlEncoded
    @POST("api/public/event-batch")
    suspend fun sendEventsBatchPublic(
        @FieldMap parameters: Map<String, String>,
    ): SendMessageBatchResponse
}
