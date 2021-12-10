package com.example.coderadar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coderadar.data.model.Contest

@Database(entities = [Contest::class],
    version = 1,
    exportSchema = false
)
abstract class CLISTDatabase : RoomDatabase() {
    abstract fun contestDao(): ContestDao

}