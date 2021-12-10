package com.example.coderadar.presentation.di

import com.example.coderadar.data.repository.dataSource.ContestCacheDataSource
import com.example.coderadar.data.repository.dataSourceImpl.ContestCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheDataModule {
    @Singleton
    @Provides
    fun provideContestCacheDataSource():ContestCacheDataSource{
        return ContestCacheDataSourceImpl()
    }
}