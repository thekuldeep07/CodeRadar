package com.example.coderadar.mvvm

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LoginRepo(val application: Application) {
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    val firebaseUserData = MutableLiveData<FirebaseUser?>()
    val isAuthenticated = MutableLiveData<Boolean>()
    val userStatus = MutableLiveData<Boolean>()

    fun isAuthenticated(){
        val user = mAuth.currentUser
        if (user != null){
            isAuthenticated.postValue(true)
        } else {
            isAuthenticated.postValue(false)
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
                    firebaseUserData.postValue(mAuth.currentUser)
                    mAuth.currentUser!!.displayName?.let { it1 -> mAuth.currentUser!!.email?.let { it2 ->
                        addingDataToFirestore(it1,
                            it2
                        )
                    } }
                }
            }
            .addOnFailureListener {
                Toast.makeText(application, "Something Went Wrong", Toast.LENGTH_LONG)
                    .show()
            }
    }


    // Login with email and pass
    fun signIn(email:String, pass:String){
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    firebaseUserData.postValue(mAuth.currentUser)
                    userStatus.postValue(true)
                }
            }
    }

    // Sign Out
    fun signOut(){
        mAuth.signOut()
        userStatus.postValue(true)
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
                userStatus.postValue(false)
            }
    }

    fun showDetails(){
        val user = FirebaseAuth.getInstance().currentUser!!
        val db = FirebaseFirestore.getInstance()
        val firebaseUser = db.collection("Users").document(user.uid).get()
        Toast.makeText(application, "${firebaseUser.result!!["Email"]} and ${firebaseUser.result!!["Name"]}",
            Toast.LENGTH_LONG).show()
    }

}