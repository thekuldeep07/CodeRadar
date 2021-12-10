package com.example.coderadar.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.coderadar.data.model.APIResponse
import com.example.coderadar.data.model.Contest
import com.example.coderadar.data.util.Resource
import com.example.coderadar.domain.usecase.GetContestUseCase
import com.example.coderadar.domain.usecase.UpdateContestUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime
import javax.inject.Inject

class ContestsViewModel  @Inject constructor(
    private val app:Application ,
    private val getContestUseCase: GetContestUseCase,
    private val updateContestUseCase: UpdateContestUseCase
):AndroidViewModel(app){

    fun getContest(resource:String,start_dt:LocalDateTime,end_dt:LocalDateTime) = liveData {
        val contestList:List<Contest>? = getContestUseCase.execute(resource,start_dt,end_dt)
        emit(contestList)
    }

    fun updateContest(resource: String,start_dt: LocalDateTime,end_dt: LocalDateTime) = liveData{
        val contestList = updateContestUseCase.execute(resource, start_dt, end_dt)
        emit(contestList)
    }


}