package com.example.coderadar.data.repository.dataSource

import com.example.coderadar.data.model.APIResponse
import retrofit2.Response
import java.time.LocalDateTime

interface ContestRemoteDataSource {
    suspend fun getPresentContest(start_dt: LocalDateTime):Response<APIResponse>
}