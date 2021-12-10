package com.example.coderadar.presentation.di

import android.app.Application
import com.example.coderadar.domain.usecase.GetContestUseCase
import com.example.coderadar.domain.usecase.UpdateContestUseCase
import com.example.coderadar.presentation.viewmodel.ContestsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideViewModelFactory
                (application: Application,
                 getContestUseCase: GetContestUseCase,
                 updateContestUseCase: UpdateContestUseCase
    ): ContestsViewModelFactory {
        return ContestsViewModelFactory(application,getContestUseCase,updateContestUseCase)
    }
}