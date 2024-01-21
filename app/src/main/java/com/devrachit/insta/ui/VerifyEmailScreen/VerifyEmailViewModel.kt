package com.devrachit.insta.ui.VerifyEmailScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Constants.Constants.Companion.USER_NODE
import com.devrachit.insta.Models.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf


@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
    val sharedViewModel: SharedViewModel
) : ViewModel(){
    val email = sharedViewModel.email
    val userEmailVerified =mutableStateOf(false)
    val loading= mutableStateOf(false)
    private val _inProgress = MutableStateFlow(false)
    val inProgress= _inProgress.asStateFlow()


        fun sendEmailVerification() {
            viewModelScope.launch {
                _inProgress.value=true
                auth.currentUser?.sendEmailVerification()
                _inProgress.value=false
            }
        }

        fun checkEmailVerification(): Boolean {
            println(email)
            Log.d("email",email)
            viewModelScope.launch {
                _inProgress.value=true
                auth.currentUser?.reload()
                userEmailVerified.value = auth.currentUser?.isEmailVerified == true
                if(userEmailVerified.value){
                    db.collection(USER_NODE).document(auth.currentUser?.uid!!).update("emailVerified",true)
                }
                _inProgress.value=false
            }
            return auth.currentUser?.isEmailVerified == true
        }
}