package com.devrachit.insta.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Models.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.time.times

@HiltViewModel
class LoginViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
    val sharedViewModel: SharedViewModel
) : ViewModel() {

    val emailValid = mutableStateOf(true)
    val passwordValid = mutableStateOf(true)
    val loading = mutableStateOf(false)
    val loginComplete = mutableStateOf(false)
    val userEmailVerified= mutableStateOf(false)
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


    fun loginUser(email: String, password: String) {
        sharedViewModel.email = email
        sharedViewModel.password = password
        sharedViewModel.userName = (Math.random()*100000).toString()
        viewModelScope.launch {
            loading.value = true
            try {
                val signInResult = auth.signInWithEmailAndPassword(email, password).await()
                if (signInResult.user != null) {
                    loginComplete.value = true
                    auth.currentUser?.reload()?.await()
                    if (auth.currentUser?.isEmailVerified == false) {
                        auth.currentUser?.sendEmailVerification()?.await()
                        println("Email Sent")
                        if (auth.currentUser?.isEmailVerified == true) {
                            userEmailVerified.value = true
                        } else {
                            // Handle the case where email verification failed
                            println("Email verification failed")
                        }
                    } else {
                        userEmailVerified.value = true
                    }
                } else {
                    loginComplete.value = false
                }
            } catch (e: Exception) {
                // Handle exceptions (e.g., network issues, Firebase errors)
                println("Login failed. Exception: ${e.message}")
            } finally {
                loading.value = false
            }
        }
    }

}