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
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val auth : FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
    val sharedViewModel: SharedViewModel
) : ViewModel(){

    val load= mutableStateOf(false)
        suspend fun checkUserLoggedIn(): Boolean {
//            viewModelScope.launch {
                if (auth.currentUser != null) {
                    auth.currentUser?.reload()
                    if (auth.currentUser?.isEmailVerified == true) {
                        sharedViewModel.email = auth.currentUser?.email.toString()
                        sharedViewModel.userName = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("userName").toString()
                        //sharedViewModel.password = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("password").toString()
                        sharedViewModel.emailVerified = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("emailVerified").toString().toBoolean()
                        sharedViewModel.uid = auth.currentUser?.uid.toString()

                    }
//                }
                load.value=true
            }
            return auth.currentUser != null
        }

        fun checkIfUserIsLoggedIn(): Boolean {
            return auth.currentUser != null
        }
        fun checkEmailVerified(): Boolean {
            return auth.currentUser?.isEmailVerified == true
        }

        fun getUserData() {
            sharedViewModel.email = auth.currentUser?.email.toString()
            sharedViewModel.userName = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("userName").toString()
            //sharedViewModel.password = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("password").toString()
            sharedViewModel.emailVerified = db.collection("users").document(auth.currentUser?.uid!!).get().result?.get("emailVerified").toString().toBoolean()
            sharedViewModel.uid = auth.currentUser?.uid.toString()
        }
}