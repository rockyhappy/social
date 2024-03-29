package com.devrachit.insta.ui.LoginScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.devrachit.insta.Constants.Constants.Companion.userVerify
import com.devrachit.insta.Models.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
//    val sharedViewModel: SharedViewModel
) : ViewModel() {

    val emailValid = mutableStateOf(true)
    val passwordValid = mutableStateOf(true)
    val loading = mutableStateOf(false)
    val loginComplete = mutableStateOf(false)
    val userEmailVerified= mutableStateOf(false)

    private val _inProgress = MutableStateFlow(false)
    val inProgress= _inProgress.asStateFlow()

    fun loginCheck(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            emailValid.value = false
            return false
        }
        if (password.isEmpty()) {
            passwordValid.value = false
            return false
        }
        return true
    }
    val sharedViewModel = SharedViewModel()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loading.value = true
            _inProgress.value=true
            try {
                val signInResult = auth.signInWithEmailAndPassword(email, password).await()
                if (auth.currentUser?.isEmailVerified == true) {
                    userEmailVerified.value = true
                    userVerify=true
                }
                if (signInResult.user != null) {
                    auth.currentUser?.reload()?.await()
                    sharedViewModel.email = email
                    sharedViewModel.password = password
                    sharedViewModel.userName = db.collection("users").document(auth.currentUser?.uid!!).get().await().get("userName").toString()
                    sharedViewModel.emailVerified = db.collection("users").document(auth.currentUser?.uid!!).get().await().get("emailVerified").toString().toBoolean()
                    println("Email Verified: ${auth.currentUser?.isEmailVerified}")
                    if (
                        auth.currentUser?.isEmailVerified == false ||
                        !sharedViewModel.emailVerified
                        )
                    {
                        auth.currentUser?.sendEmailVerification()?.await()
                        println("Email Sent")
                        if (auth.currentUser?.isEmailVerified == true) {
                            userEmailVerified.value = true
                            userVerify=true
                        } else {
                            println("Email verification failed")
                        }
                    } else {
                        userEmailVerified.value = true
                    }
                } else {
                    println("Login failed")
                }
            } catch (e: Exception) {
                println("Login failed. Exception: ${e.message}")
            } finally {
                loginComplete.value = true
                loading.value = false
                _inProgress.value=false
            }
        }
    }

    fun updateUser(email:String,userName:String,uid:String)
    {
        sharedViewModel.email=email
        sharedViewModel.userName=userName
        sharedViewModel.uid=uid
    }
    fun sendPasswordResetEmail(email: String) {
        _inProgress.value = true
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email).await()
            } catch (e: Exception) {
                println("Password reset failed. Exception: ${e.message}")
            }
            finally {
                _inProgress.value = false
            }
        }
    }
}