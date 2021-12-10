package com.example.coderadar.presentation.di

import com.example.coderadar.data.repository.ContestRepositoryImpl
import com.example.coderadar.data.repository.dataSource.ContestCacheDataSource
import com.example.coderadar.data.repository.dataSource.ContestLocalDataSource
import com.example.coderadar.data.repository.dataSource.ContestRemoteDataSource
import com.example.coderadar.domain.repository.ContestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideContestRepository(contestRemoteDataSource: ContestRemoteDataSource,
                                 contestLocalDataSource: ContestLocalDataSource,
                                 contestCacheDataSource: ContestCacheDataSource): ContestRepository {
        return ContestRepositoryImpl(contestRemoteDataSource,contestLocalDataSource,contestCacheDataSource)
    }
}