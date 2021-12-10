package com.example.coderadar.domain.usecase

import com.example.coderadar.data.model.APIResponse
import com.example.coderadar.data.model.Contest
import com.example.coderadar.data.util.Resource
import com.example.coderadar.domain.repository.ContestRepository
import java.time.LocalDateTime

class GetContestUseCase(private  val  contestRepository: ContestRepository) {
    suspend fun execute(resource:String,start_dt:LocalDateTime,end_dt:LocalDateTime): List<Contest>? {
        return contestRepository.getContests(resource,start_dt,end_dt)
    }
}