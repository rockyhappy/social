package com.devrachit.insta.viewModel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Constants.Constants.Companion.USER_NODE
import com.devrachit.insta.hilt.Application
import com.devrachit.insta.util.isValidEmail
import com.devrachit.insta.util.isValidPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LCViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
    @ApplicationContext context: Context,
    val sharedViewModel: SharedViewModel
) : ViewModel() {


    val emailValid = mutableStateOf(true)
    val userNameValid = mutableStateOf(true)
    val passwordValid = mutableStateOf(true)
    val userNameErrorMessage = mutableStateOf("")
    val loading = mutableStateOf(false)
    val signupComplete = mutableStateOf(false)
    val userData = mutableStateOf(hashMapOf<String, Any>())
    var emailData = ""
    val userEmailVerified= mutableStateOf(false)

    private val _userNameValidationResult = MutableLiveData<Boolean>()
    val userNameValidationResult: LiveData<Boolean> get() = _userNameValidationResult

    fun validateEmail(email: String): Boolean {
        emailData = email.trim()
        loading.value = true
        if (isValidEmail(email)) {
            emailValid.value = true
            return true
        }

        emailValid.value = false
        loading.value = false
        return false
    }

    fun userNameValidation(userName: String, email: String, password: String) {
        var ret = false
        viewModelScope.launch {
            ret = validateUserName(userName, email, password)
            _userNameValidationResult.value = ret
        }

    }

    suspend fun validateUserName(userName: String, email: String, password: String): Boolean {
        if (userName.isEmpty() || userName.length < 4) {
            userNameValid.value = false
            userNameErrorMessage.value = "Username must be at least 4 characters"
            return false
        }

        val taken = userNameAlreadyTaken(userName, email, password) ?: false
        println("taken: $taken")

        if (taken) {
            println("Username already taken")
            userNameErrorMessage.value = "Username already taken"
        }
        userNameValid.value = !taken
        return !taken
    }

    fun validatePassword(password: String): Boolean {
        loading.value = true
        if (isValidPassword(password)) {
            passwordValid.value = true
            return true
        }
        passwordValid.value = false
        loading.value = false
        return false
    }

    fun signUp(email: String, password: String, userName: String) {
        loading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                println("Email Sent")
                            }
                        }
                        ?.addOnFailureListener {
                            println("Email not sent")
                        }

                    sharedViewModel.email = email
                    sharedViewModel.userName = userName
                    sharedViewModel.password = password
//                    val user = hashMapOf(
//                        "email" to email,
//                        "userName" to userName,
//                        "uid" to auth.currentUser?.uid,
//                        "profilePic" to "https://firebasestorage.googleapis.com/v0/b/insta-clone-1e9f0.appspot.com/o/profilePic%2Fdefault.png?alt=media&token=8b8b8b1a-7b9e-4b9e-9b0a-9b9b9b9b9b9b",
//                        "emailVerified" to false,
//                    )
//
//                    db.collection(USER_NODE).document(auth.currentUser?.uid.toString()).set(user)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                println("User Created")
//                            }
//                            loading.value=false
//                            signupComplete.value=true
//                        }
                    loading.value = false
                    signupComplete.value = true
                }
                loading.value = false
            }
    }

    suspend fun userNameAlreadyTaken(userName: String, email: String, password: String): Boolean? {
        return suspendCoroutine { continuation ->
            db.collection(USER_NODE).get().addOnSuccessListener { querySnapshot ->
                var taken = false
                for (document in querySnapshot) {
                    println("document: ${document.data["userName"]}")
                    if (document.data["userName"] == userName) {
                        taken = true
                        break
                    }
                }
                if (!taken) {
                    signUp(email, password, userName)
                }

                println("taken: $taken")
                loading.value = false
                continuation.resume(taken)
            }.addOnFailureListener {
                continuation.resume(false)
            }
        }
    }

    fun refreshEmailVerification() {
        auth.currentUser?.reload()
        if (auth.currentUser?.isEmailVerified == true) {
                userEmailVerified.value = true
            val user = hashMapOf(
                "email" to sharedViewModel.email,
                "userName" to sharedViewModel.userName,
                "uid" to auth.currentUser?.uid,
                "profilePic" to "https://firebasestorage.googleapis.com/v0/b/insta-clone-1e9f0.appspot.com/o/profilePic%2Fdefault.png?alt=media&token=8b8b8b1a-7b9e-4b9e-9b0a-9b9b9b9b9b9b",
                "emailVerified" to auth.currentUser?.isEmailVerified
            )

            db.collection(USER_NODE).document(auth.currentUser?.uid.toString()).set(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("User Created")
                    }
                    loading.value = false
                    signupComplete.value = true
                }
        }
    }

}