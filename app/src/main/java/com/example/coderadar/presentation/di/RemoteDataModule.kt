package com.example.coderadar.presentation.di


import com.example.coderadar.data.repository.dataSource.ContestRemoteDataSource
import com.example.CodeRadar.data.repository.dataSourceImpl.PresentContestRemoteDataSourceImpl
import com.example.coderadar.data.api.ClistAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun providePresentContestRemoteDataSource(clistAPIService: ClistAPIService): ContestRemoteDataSource {
        return PresentContestRemoteDataSourceImpl(clistAPIService)
    }
}