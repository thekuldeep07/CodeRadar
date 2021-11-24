package com.example.coderadar.data.api

import com.example.coderadar.data.model.APIResponse
import com.example.CodeRadar.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime


interface ClistAPIService {
    @GET("/api/v2/json/contest/")
    suspend fun getContestPresent(
        @Query("username") username:String = BuildConfig.USERNAME,
        @Query("api_key") api_key:String = BuildConfig.API_KEY,
        @Query("resource")resource:String,
        @Query("start__gt") start_dt: LocalDateTime,
        @Query("end__gt")end_dt:LocalDateTime,
        @Query("order_by") order_by:String = "start",

        ): Response<APIResponse>

}