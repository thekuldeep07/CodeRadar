package com.example.CodeRadar.data.repository.dataSourceImpl

import com.example.coderadar.data.model.APIResponse
import com.example.coderadar.data.repository.dataSource.ContestRemoteDataSource
import com.example.coderadar.data.api.ClistAPIService
import retrofit2.Response
import java.time.LocalDateTime

class PresentContestRemoteDataSourceImpl(
    private val clistAPIService: ClistAPIService,

    ): ContestRemoteDataSource {
    override suspend fun getPresentContest(
        resource:String,
        start_dt: LocalDateTime,
        end_dt:LocalDateTime

    ): Response<APIResponse> {
        return clistAPIService.getContestPresent(resource = resource,start_dt = start_dt,end_dt = end_dt)

    }
}