package com.devrachit.insta.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devrachit.insta.Constants.Constants.Companion.USER_NODE
import com.devrachit.insta.Models.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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
        fun sendEmailVerification() {
            auth.currentUser?.sendEmailVerification()
        }

        fun checkEmailVerification(): Boolean {
            loading.value=true
            auth.currentUser?.reload()
            userEmailVerified.value = auth.currentUser?.isEmailVerified == true
            if(userEmailVerified.value){
                db.collection(USER_NODE).document(auth.currentUser?.uid!!).update("emailVerified",true)
            }
            loading.value=false
            return auth.currentUser?.isEmailVerified == true
        }
}