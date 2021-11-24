package com.example.coderadar.data.repository

import com.example.coderadar.data.model.APIResponse
import com.example.coderadar.data.model.Contest
import com.example.coderadar.data.repository.dataSource.ContestRemoteDataSource
import com.example.coderadar.data.util.Resource
import com.example.coderadar.domain.repository.ContestRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.time.LocalDateTime

class ContestRepositoryImpl(
        private  val remoteDataSource: ContestRemoteDataSource
): ContestRepository {
    override suspend fun getContests(
        resource:String,
        start_dt: LocalDateTime,
        end_dt:LocalDateTime
    ): Resource<APIResponse> {
        return responseToResource(remoteDataSource.getPresentContest(resource,start_dt,end_dt))
    }


    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful){
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getSearchedContest(searchQuery: String): Resource<APIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveContest(contest: Contest) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContest(contest: Contest) {
        TODO("Not yet implemented")
    }

    override fun getSavedContest(): Flow<List<Contest>> {
        TODO("Not yet implemented")
    }
}