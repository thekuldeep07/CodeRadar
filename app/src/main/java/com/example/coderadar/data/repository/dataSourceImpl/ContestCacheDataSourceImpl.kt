package com.example.coderadar.data.repository.dataSourceImpl

import com.example.coderadar.data.model.Contest
import com.example.coderadar.data.repository.dataSource.ContestCacheDataSource

class ContestCacheDataSourceImpl : ContestCacheDataSource {
    private  var contestList = ArrayList<Contest>()
    override suspend fun getContestsFromCache(): List<Contest> {
        return contestList
    }

    override suspend fun saveContestsToCache(contests: List<Contest>) {
        contestList.clear()
        contestList = ArrayList(contests)
    }
}