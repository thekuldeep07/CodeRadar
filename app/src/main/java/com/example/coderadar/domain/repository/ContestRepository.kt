package com.example.coderadar.domain.repository

import com.example.coderadar.data.model.APIResponse
import com.example.coderadar.data.model.Contest
import com.example.coderadar.data.util.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ContestRepository {

    suspend fun getContests(start_dt:LocalDateTime,end_dt:LocalDateTime): Resource<APIResponse>

    suspend fun  getSearchedContest(searchQuery:String): Resource<APIResponse>

    suspend fun saveContest(contest: Contest)
    suspend fun deleteContest(contest: Contest)
    fun getSavedContest(): Flow<List<Contest>>


}