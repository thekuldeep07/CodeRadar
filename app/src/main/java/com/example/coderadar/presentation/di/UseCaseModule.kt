package com.example.coderadar.presentation.di

import com.example.coderadar.domain.repository.ContestRepository
import com.example.coderadar.domain.usecase.GetContestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {


    @Provides
    @Singleton
    fun provideGetContestUseCase(contestRepository: ContestRepository): GetContestUseCase {
        return GetContestUseCase(contestRepository)
    }
}