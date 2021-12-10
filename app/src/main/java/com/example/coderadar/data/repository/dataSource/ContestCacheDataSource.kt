package com.example.coderadar.data.repository.dataSource

import com.example.coderadar.data.model.Contest

interface ContestCacheDataSource {

    suspend fun getContestsFromCache():List<Contest>
    suspend fun saveContestsToCache(contests:List<Contest>)
}