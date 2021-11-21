package com.example.coderadar.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coderadar.domain.usecase.GetContestUseCase

class ContestsViewModelFactory(
    private val app:Application,
    private val getContestUseCase: GetContestUseCase
) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContestsViewModel(
            app,
            getContestUseCase
        ) as T
    }
}