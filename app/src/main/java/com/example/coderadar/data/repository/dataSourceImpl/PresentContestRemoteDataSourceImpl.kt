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
        start_dt: LocalDateTime,
    ): Response<APIResponse> {
        return clistAPIService.getContestPresent(start_dt = start_dt)

    }
}