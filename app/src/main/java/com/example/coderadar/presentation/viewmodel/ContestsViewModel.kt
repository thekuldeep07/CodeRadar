package com.example.coderadar.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coderadar.data.model.APIResponse
import com.example.coderadar.data.util.Resource
import com.example.coderadar.domain.usecase.GetContestUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime
import javax.inject.Inject

class ContestsViewModel  @Inject constructor(
    private val app:Application ,
    private val getContestUseCase: GetContestUseCase
):AndroidViewModel(app){
    val contestDetails:MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getContest(start_dt:LocalDateTime,end_dt:LocalDateTime)=viewModelScope.launch(Dispatchers.IO) {
        contestDetails.postValue(Resource.Loading())
        try {
            if(isInternetAvailable(app)){

                val apiResult = getContestUseCase.execute(start_dt,end_dt)
                contestDetails.postValue(apiResult)
            }
            else{
                contestDetails.postValue(Resource.Error("Internet is not available."))
            }
        }
        catch (e:Exception){
            contestDetails.postValue(Resource.Error(e.message.toString()))
        }


    }


    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }
}