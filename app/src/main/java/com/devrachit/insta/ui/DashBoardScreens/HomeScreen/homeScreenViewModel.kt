package com.devrachit.insta.ui.DashBoardScreens.HomeScreen

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class homeScreenViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,

): ViewModel(){



    private val _loading = MutableStateFlow(false)
    val loading= _loading.asStateFlow()

    private val _inProgress = MutableStateFlow(false)
    val inProgress= _inProgress.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email= MutableStateFlow("")
    val email : StateFlow<String> =_email

    private val _following = MutableStateFlow(0)
    val following : StateFlow<Int> =_following

    private val _followers = MutableStateFlow(0)
    val followers : StateFlow<Int> =_followers

    private val _postCount = MutableStateFlow(0)
    val postCount : StateFlow<Int> =_postCount

    private val _profileImage = MutableStateFlow("")
    val profileImage : StateFlow<String> =_profileImage


    fun getUserData(viewMo: ProfileSharedViewModel) : String
    {
        viewModelScope.launch {
            _loading.value=true
            try{
                auth.currentUser?.let {
                    val uid = it.uid
                    val docRef = db.collection("users").document(uid)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                _username.value = document.data?.get("userName").toString()
                                _email.value = document.data?.get("email").toString()
                                _following.value = document.data?.get("following").toString().toInt()
                                _followers.value = document.data?.get("followers").toString().toInt()
                                _profileImage.value = document.data?.get("profilePic").toString()
                                _postCount.value = document.data?.get("postCount").toString().toInt()

                                println("DocumentSnapshot data: ${document.data}")

                            }
                        }
                        .addOnFailureListener{
                            println("Error getting documents: ${it}")
                        }

                }
            }
            catch(e: Exception)
            {
                println("Error getting documents: ${e}")
            }
            finally {
                _loading.value=false
                _inProgress.value=true
            }


        }
        println("Username: "+_username.value.toString())
        return _username.value.toString()
    }
}