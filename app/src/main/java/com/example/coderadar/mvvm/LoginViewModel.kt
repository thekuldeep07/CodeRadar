package com.example.coderadar.mvvm

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coderadar.data.model.Contest
import com.example.coderadar.mvvm.LoginRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application) {
    val repository : LoginRepo
    var firebaseUserData : MutableLiveData<FirebaseUser?>
    val userLogOutStatus : MutableLiveData<Boolean>
    val contestSavedStatus : MutableLiveData<Boolean>
    val isAuthenticated : MutableLiveData<Boolean>
    val passStatus : MutableLiveData<Boolean>
    init {
        repository = LoginRepo(application)
        firebaseUserData = repository.firebaseUserData
        userLogOutStatus = repository.userLogoutStatus
        isAuthenticated = repository.isAuthenticated
        contestSavedStatus = repository.contestSavedStatus
        passStatus = repository.passwordStatus
    }

    fun signUp(name: String, email:String, pass:String) = viewModelScope.launch {
        repository.createUser(name, email, pass)
    }

    fun signIn(email:String, pass:String) = viewModelScope.launch {
        repository.signIn(email, pass)
    }

    fun loginWithGoogleAccount(idToken: String) {
        repository.signInWithGoogle(idToken)
    }

    fun signOut() {
        repository.signOut()
    }

    fun addingDataToFirebase(name:String, email:String) {
        repository.addingDataToFirestore(name, email)
    }

    fun isAuthenticated() {
        repository.isAuthenticated()
    }

    fun showDetails() {
        repository.showDetails()
    }

    fun removefirebaseUserData() {
        firebaseUserData.postValue(null)
    }

    fun addContestintofirebase(contest: Contest) {
        repository.addingContestToFirestore(contest)
    }

    fun resetPassword(email: String) {
        repository.resetPassword(email)
    }
}

