package com.example.coderadar.presentation.di

import com.example.coderadar.data.db.ContestDao
import com.example.coderadar.data.repository.dataSource.ContestLocalDataSource
import com.example.coderadar.data.repository.dataSourceImpl.ContestLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideContestLocalDataSource(contestDao: ContestDao):ContestLocalDataSource{
        return ContestLocalDataSourceImpl(contestDao)
    }
}