package com.example.coderadar.domain.usecase

import com.example.coderadar.data.model.Contest
import com.example.coderadar.domain.repository.ContestRepository
import java.time.LocalDateTime

class UpdateContestUseCase(private  val contestRepository: ContestRepository) {
    suspend fun execute(resource:String, start_dt: LocalDateTime, end_dt: LocalDateTime):List<Contest>?
    = contestRepository.updateContests(resource,start_dt,end_dt)
}