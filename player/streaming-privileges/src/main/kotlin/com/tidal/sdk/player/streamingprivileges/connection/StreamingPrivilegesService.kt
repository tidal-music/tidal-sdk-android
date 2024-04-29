package com.tidal.sdk.player.streamingprivileges.connection

import retrofit2.Call
import retrofit2.http.POST

internal interface StreamingPrivilegesService {

    @POST("rt/connect")
    fun getStreamingPrivilegesWebSocketInfo(): Call<StreamingPrivilegesWebSocketInfo>
}
