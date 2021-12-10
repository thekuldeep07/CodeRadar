package com.example.coderadar.data.repository.dataSourceImpl

import com.example.coderadar.data.db.ContestDao
import com.example.coderadar.data.model.Contest
import com.example.coderadar.data.repository.dataSource.ContestLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContestLocalDataSourceImpl(private  val contestDao: ContestDao):
ContestLocalDataSource
{
    override suspend fun getContestsFromDB(resourceName:String): List<Contest> {
        return contestDao.getContests(resourceName)
    }



    override suspend fun saveContestsToDB(contests: List<Contest>) {
        CoroutineScope(Dispatchers.IO).launch {
            contestDao.saveContests(contests)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            contestDao.deleteAllContests()
        }
    }
}