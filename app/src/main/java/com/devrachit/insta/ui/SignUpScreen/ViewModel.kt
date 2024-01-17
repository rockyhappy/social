package com.devrachit.insta.ui.SignUpScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Constants.Constants.Companion.USER_NODE
import com.devrachit.insta.Models.SharedViewModel
import com.devrachit.insta.util.isValidEmail
import com.devrachit.insta.util.isValidPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LCViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
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

    private val _inProgress = MutableStateFlow(false)
    val inProgress= _inProgress.asStateFlow()

    //This function is used to validate the email
    fun validateEmail(email: String): Boolean {
        emailData = email.trim()
        _inProgress.value=true
        loading.value = true
        if (isValidEmail(email)) {
            emailValid.value = true
            _inProgress.value=false
            return true
        }

        emailValid.value = false
        loading.value = false
        _inProgress.value=false
        return false
    }

    //This function is used to validate the username post to the check if the userName is already taken
    fun validatePreUserNameBeforeSignup(userName:String) :Boolean
    {
        _inProgress.value=true
        sharedViewModel.userName=userName
        if(userName.length<6 || userName.isEmpty())
        {
            userNameErrorMessage.value="Username must be at least 6 characters"
            userNameValid.value=false
            _inProgress.value=false
            return false
        }
        if(userName.contains(" "))
        {
            userNameErrorMessage.value="Username must not contain spaces"
            userNameValid.value=false
            _inProgress.value=false
            return false
        }
        _inProgress.value=false
        return true
    }

    fun userNameValidation(userName: String, email: String, password: String) {
        var ret = false
        _inProgress.value=true
        viewModelScope.launch {
            ret = validateUserName(userName, email, password)
            _userNameValidationResult.value = ret
            _inProgress.value=false
        }

    }

    suspend fun validateUserName(userName: String, email: String, password: String): Boolean {
        _inProgress.value=true
        if (userName.isEmpty() || userName.length < 6) {
            userNameValid.value = false
            userNameErrorMessage.value = "Username must be at least 4 characters"
            _inProgress.value=false
            return false
        }
        val taken = userNameAlreadyTaken(userName, email, password) ?: false
        println("taken: $taken")

        if (taken) {
            println("Username already taken")
            userNameErrorMessage.value = "Username already taken"
        }
        userNameValid.value = !taken
        _inProgress.value=false
        return !taken
    }

    //This function is used to validate the password
    fun validatePassword(password: String): Boolean {
        loading.value = true
        _inProgress.value=true
        if (isValidPassword(password)) {
            passwordValid.value = true
            loading.value = false
            _inProgress.value=false
            return true
        }
        passwordValid.value = false
        _inProgress.value=false
        loading.value = false
        return false
    }

    fun signUp(email: String, password: String, userName: String) {
        loading.value = true
        _inProgress.value=true
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
                    val user = hashMapOf(
                        "email" to email,
                        "userName" to userName,
                        "uid" to auth.currentUser?.uid,
                        "profilePic" to "https://firebasestorage.googleapis.com/v0/b/twingle-c1acb.appspot.com/o/avatar.png?alt=media&token=cc768fc6-57c1-4326-a874-4f0fd3bbe5de",
                        "emailVerified" to false,
                    )

                    db.collection(USER_NODE).document(auth.currentUser?.uid.toString()).set(user)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                println("User Created")
                            }
                            _inProgress.value=false
                        }


                    sharedViewModel.email = email
                    sharedViewModel.userName = userName
                    sharedViewModel.password = password
                    loading.value = false
                    signupComplete.value = true
                }
                loading.value = false
                _inProgress.value=false
            }
    }

    suspend fun userNameAlreadyTaken(userName: String, email: String, password: String): Boolean? {
        loading.value = true
        _inProgress.value=true
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
                _inProgress.value=false
            }.addOnFailureListener {
                _inProgress.value=false
                continuation.resume(false)
            }
        }
    }

    fun refreshEmailVerification() {
        loading.value = true
        auth.currentUser?.reload()?.addOnCompleteListener { reloadTask ->
            if (reloadTask.isSuccessful) {
                println("Reloaded")
            }
            loading.value = true
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
        loading.value = false
    }

    fun createUser(email :String , UserName : String, Uid :String)
    {
        sharedViewModel.email=email
        sharedViewModel.uid=Uid
        sharedViewModel.userName=UserName
        val user = hashMapOf(
            "email" to sharedViewModel.email,
            "userName" to sharedViewModel.userName,
            "uid" to auth.currentUser?.uid,
            "profilePic" to "https://firebasestorage.googleapis.com/v0/b/insta-clone-1e9f0.appspot.com/o/profilePic%2Fdefault.png?alt=media&token=8b8b8b1a-7b9e-4b9e-9b0a-9b9b9b9b9b9b",
            "emailVerified" to true
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