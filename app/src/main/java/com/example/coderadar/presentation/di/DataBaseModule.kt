package com.example.coderadar.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.coderadar.data.db.CLISTDatabase
import com.example.coderadar.data.db.ContestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideContestDataBase(@ApplicationContext context: Context):CLISTDatabase{
        return Room.databaseBuilder(context,CLISTDatabase::class.java,"clistClient").build()
    }

    @Singleton
    @Provides
    fun provideContestDao(clistDatabase: CLISTDatabase):ContestDao{
        return clistDatabase.contestDao()
    }
}