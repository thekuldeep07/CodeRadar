package com.example.coderadar.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coderadar.data.model.Contest

@Dao
interface ContestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContests(artists : List<Contest>)

    @Query("DELETE FROM contests_table")
    suspend fun  deleteAllContests()

    @Query("SELECT * FROM contests_table WHERE resource = :resourceName")
    suspend fun getContests(resourceName:String):List<Contest>

}