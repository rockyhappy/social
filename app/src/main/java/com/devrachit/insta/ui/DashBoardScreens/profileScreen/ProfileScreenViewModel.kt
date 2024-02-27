package com.devrachit.insta.ui.DashBoardScreens.profileScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Constants.Constants.Companion.POST_NODE
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
) : ViewModel() {

    val posts= mutableListOf<String>()
    var post= listOf<String>()

    fun loadUserPosts() {
        viewModelScope.launch {
            try {
                auth.currentUser?.let {
                    val uid = it.uid
                    val docRef = db.collection("users").document(uid)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            //how to access a collection made inside this document

                            document.reference.collection(POST_NODE).get()
                                .addOnSuccessListener {querySnapshot ->
                                    for (document in querySnapshot) {
                                        //do something with the data
                                        posts.add(document.data["imageUrl"].toString())
                                        println(document.data["imageUrl"].toString())
                                    }


                            }

                        }
                }
            } catch (e: Exception) {

            } finally {

            }
        }
    }
}