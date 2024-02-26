package com.remees.findmyip.feature_findip.data.remote

import com.remees.findmyip.feature_findip.data.remote.dto.IpInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FindMyIpApi {

    @GET("/json/")
    suspend fun getIpInfo(): IpInfoDto

    companion object {
        const val BASE_URL = "https://ipapi.co/"
    }
}