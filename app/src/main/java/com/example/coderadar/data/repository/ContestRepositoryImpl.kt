package com.example.coderadar.data.repository

import android.util.Log
import com.example.coderadar.data.model.APIResponse
import com.example.coderadar.data.model.Contest
import com.example.coderadar.data.repository.dataSource.ContestCacheDataSource
import com.example.coderadar.data.repository.dataSource.ContestLocalDataSource
import com.example.coderadar.data.repository.dataSource.ContestRemoteDataSource
import com.example.coderadar.data.util.Resource
import com.example.coderadar.domain.repository.ContestRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.lang.Exception
import java.time.LocalDateTime

class ContestRepositoryImpl(
        private  val remoteDataSource: ContestRemoteDataSource,
        private val  localDataSource: ContestLocalDataSource,
        private val  cacheDataSource: ContestCacheDataSource
): ContestRepository {
    override suspend fun getContests(
        resource: String,
        start_dt: LocalDateTime,
        end_dt:LocalDateTime
    ): List<Contest>? {
        return getContestsFromDB(resource ,start_dt,end_dt)
    }




    override suspend fun updateContests(resource: String,
                                        start_dt: LocalDateTime,
                                        end_dt:LocalDateTime): List<Contest>?{

        val newContestList : List<Contest>? = getContestFromAPI(start_dt,end_dt)
        localDataSource.clearAll()

        if (newContestList != null) {
            localDataSource.saveContestsToDB(newContestList)
//            cacheDataSource.saveContestsToCache(newContestList)
        }
        val newContestList1:List<Contest>? = getContestsFromDB(resource,start_dt,end_dt)
        return  newContestList1
    }

    suspend fun  getContestFromAPI(
        start_dt: LocalDateTime,
        end_dt:LocalDateTime
    ): List<Contest>? {
        var contestList: List<Contest>? = null
        try {
            val response = remoteDataSource.getPresentContest(start_dt,end_dt)
            val body = response.body()
            if(body!=null){
                contestList = body.contests
            }
        }catch (exception:Exception){
            Log.i("newTag1",exception.message.toString())
        }
        return contestList
    }

    suspend fun getContestsFromDB(resource: String,
                                  start_dt: LocalDateTime,
                                  end_dt:LocalDateTime): List<Contest>? {

        Log.i("print", "77889")
        var contestList: List<Contest>? = null
        try {
            contestList = localDataSource.getContestsFromDB(resource)
        } catch (exception: Exception) {
            Log.i("MyTag", exception.message.toString())
        }


        if(contestList!=null&&contestList.isNotEmpty()){
                return contestList
            }else if (contestList==null||contestList.isEmpty()){
                contestList= getContestFromAPI(start_dt,end_dt)
                var newList:ArrayList<Contest>? = ArrayList()
                if (contestList != null) {
                    contestList.forEach {
                        if (it.resource==resource){
                            newList?.add(it)
                        }
                    }
                    localDataSource.saveContestsToDB(contestList)
                    return  newList
                }
//
            }



        return contestList
    }

//    suspend fun getContestsFromCache(resource:String,
//                                     start_dt: LocalDateTime,
//                                     end_dt:LocalDateTime):List<Contest>{
//        lateinit var contestList: List<Contest>
//        try {
//            contestList =cacheDataSource.getContestsFromCache()
//        } catch (exception: Exception) {
//            Log.i("MyTag", exception.message.toString())
//        }
//        if(contestList.isNotEmpty()){
//            return contestList
//        }else{
//            contestList=getContestsFromDB(resource,
//                start_dt,
//                end_dt)
//            cacheDataSource.saveContestsToCache(contestList)
//        }
//
//        return contestList
//    }

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