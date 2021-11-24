package com.example.coderadar.data.repository.dataSource

import com.example.coderadar.data.model.APIResponse
import retrofit2.Response
import java.time.LocalDateTime

interface ContestRemoteDataSource {
    suspend fun getPresentContest(resource:String,start_dt: LocalDateTime,end_dt:LocalDateTime):Response<APIResponse>
}