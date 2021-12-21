package com.example.coderadar.mvvm

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.coderadar.data.model.Contest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LoginRepo(val application: Application) {
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    val firebaseUserData = MutableLiveData<FirebaseUser?>()
    val isAuthenticated = MutableLiveData<Boolean>()
    val userLogoutStatus = MutableLiveData<Boolean>()
    val contestSavedStatus = MutableLiveData<Boolean?>()
    val passwordStatus = MutableLiveData<Boolean>()

    fun isAuthenticated(){
        val user = mAuth.currentUser
        if (user != null){
            isAuthenticated.postValue(true)
            userLogoutStatus.postValue(false)
        } else {
            isAuthenticated.postValue(false)
            userLogoutStatus.postValue(true)
        }
    }

    // Register with email and pass
    fun createUser(name: String, email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                sendEmail()
                firebaseUserData.postValue(mAuth.currentUser)
                addingDataToFirestore(name, email)
            }
            .addOnFailureListener {
                Toast.makeText(application, "${it.toString()}", Toast.LENGTH_LONG).show()
            }
    }

    // Login with Google Account
    fun signInWithGoogle(idToken: String) {
        firebaseUserData.postValue(null)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    if (it.result.additionalUserInfo?.isNewUser!!){
                        mAuth.currentUser!!.displayName?.let { it1 -> mAuth.currentUser!!.email?.let { it2 ->
                            addingDataToFirestore(it1,
                                it2
                            )
                        } }
                    }
                    userLogoutStatus.postValue(false)
                    firebaseUserData.postValue(mAuth.currentUser)

                }
            }
            .addOnFailureListener {
                firebaseUserData.postValue(null)
            }
    }


    // Login with email and pass
    fun signIn(email:String, pass:String){
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    firebaseUserData.postValue(mAuth.currentUser)
                    userLogoutStatus.postValue(false)
                }
            }
    }

    // Sign Out
    fun signOut(){
        mAuth.signOut()
        userLogoutStatus.postValue(true)
    }

    // Sending Email for varification
    private fun sendEmail(){
        val user = mAuth.currentUser
        user?.sendEmailVerification()
    }

    // Adding Data to the firebase firestore
    fun addingDataToFirestore(name: String, email: String) {
        val data = HashMap<String, String>()
        data["Name"] = name
        data["Email"] = email
        db.collection("Users")
            .add(data)
            .addOnSuccessListener {
                userLogoutStatus.postValue(false)
            }
    }

    fun addingcontestfunction(contest: Contest){
        contestSavedStatus.postValue(null)
        val email = mAuth.currentUser?.email
        var data = HashMap<String, String>()
        data["Name"] = contest.event!!
        data["Link"] = contest.href!!
        data["Start Date"] = contest.start!!
        if (checkingContest(contest.event, email!!)){
            db.collection("Contests").document("$email").collection("${contest.id}").document(contest.event)
                .set(data)
                .addOnSuccessListener {
                    contestSavedStatus.postValue(true)
                }
                .addOnFailureListener {
                    contestSavedStatus.postValue(false)
                }
        } else {
            Toast.makeText(application, "This contest is already exists.", Toast.LENGTH_LONG).show()
        }

    }

    private fun checkingContest(id: String, email: String): Boolean{
        var returnVal = true
        db.collection("Contests").document(email).collection(id)
            .get()
            .addOnSuccessListener {
                returnVal = it.isEmpty
            }

        return returnVal
    }

    fun resetPassword(Email: String) {
        mAuth.sendPasswordResetEmail(Email)
            .addOnCompleteListener {
                it?.let {
                    if (it.isSuccessful) {
                        passwordStatus.postValue(true)
                    }
                }
            }
            .addOnFailureListener {
                passwordStatus.postValue(false)
            }
    }

}