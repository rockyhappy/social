package com.devrachit.insta.ui.SplashScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SplashScreenViewModel @Inject constructor(
    val auth : FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
    val sharedViewModel: SharedViewModel
) : ViewModel(){


    private val _load = MutableStateFlow(false)
    val load = _load.asStateFlow()
    private val _inProgress = MutableStateFlow(true)
    val inProgress= _inProgress.asStateFlow()
        fun checkUserLoggedIn(): Boolean {
            _inProgress.value=true
            viewModelScope.launch {
                println("Checking User Logged In")
                Log.d("Checking User Logged In", "Checking User Logged In")
                auth.currentUser?.reload()?.await()
                if (auth.currentUser != null) {
                    val result=auth.currentUser?.reload()?.isComplete
                    if(result==true)
                    if (auth.currentUser?.isEmailVerified == true) {
                        sharedViewModel.email = auth.currentUser?.email.toString()
                        sharedViewModel.userName = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("userName").toString()
//                        sharedViewModel.password = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("password").toString()
                        sharedViewModel.emailVerified = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("emailVerified").toString().toBoolean()
                        sharedViewModel.uid = auth.currentUser?.uid.toString()

                    }
                }
                _load.value=true
                _inProgress.value=false
            }
            return auth.currentUser!=null && auth.currentUser?.isEmailVerified == true
        }

        fun checkIfUserIsLoggedIn(): Boolean {
            return auth.currentUser != null
        }
        fun checkEmailVerified(): Boolean {
            return auth.currentUser?.isEmailVerified == true
        }

        fun getUserData() {
            _inProgress.value=true
            sharedViewModel.email = auth.currentUser?.email.toString()
            sharedViewModel.userName = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("userName").toString()
            //sharedViewModel.password = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("password").toString()
            sharedViewModel.emailVerified = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("emailVerified").toString().toBoolean()
            sharedViewModel.uid = auth.currentUser?.uid.toString()
            _inProgress.value=false
        }
}