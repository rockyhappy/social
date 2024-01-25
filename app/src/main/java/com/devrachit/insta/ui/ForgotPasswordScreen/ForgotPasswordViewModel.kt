package com.devrachit.insta.ui.ForgotPasswordScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Constants.Constants
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
class ForgotPasswordViewModel @Inject constructor(
    val auth : FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
    val sharedViewModel: SharedViewModel
) : ViewModel() {
    val email = sharedViewModel.email
    val userEmailVerified = mutableStateOf(false)
    val loading= mutableStateOf(false)

    private val _inProgress = MutableStateFlow(false)
    val inProgress= _inProgress.asStateFlow()


    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _inProgress.value=true
            auth.sendPasswordResetEmail(email).await()
            _inProgress.value=false
        }
    }
}