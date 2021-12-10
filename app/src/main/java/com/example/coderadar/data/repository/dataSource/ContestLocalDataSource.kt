package com.example.coderadar.data.repository.dataSource

import com.example.coderadar.data.model.Contest

interface ContestLocalDataSource {
    suspend fun getContestsFromDB(resourceName:String):List<Contest>?
    suspend fun saveContestsToDB(contests:List<Contest>)
    suspend fun clearAll()
}