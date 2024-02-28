package com.devrachit.insta.ui.DashBoardScreens.profileScreen

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Constants.Constants.Companion.POST_NODE
import com.devrachit.insta.Constants.Constants.Companion.TAPES_NODE
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.devrachit.insta.Models.Tapes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _inProgress = MutableStateFlow(false)
    val loadingComplete= _inProgress.asStateFlow()
    fun loadUserPosts() {
        viewModelScope.launch {
            try {
                auth.currentUser?.let {
                    val uid = it.uid
                    val docRef = db.collection("users").document(uid)
                    docRef.get()
                        .addOnSuccessListener { document ->

                            document.reference.collection(POST_NODE).get()
                                .addOnSuccessListener {querySnapshot ->
                                    for (document in querySnapshot) {
                                        //do something with the data
                                        posts.add(document.data["imageUrl"].toString())
                                        println(document.data["imageUrl"].toString())
                                    }


                            }
                                .addOnCompleteListener {
                                    _inProgress.value = true
                                }

                        }
                }
            } catch (e: Exception) {

            } finally {
            }
        }
    }

    val tapes = mutableListOf<Tapes>()
    val _tapesLoading = MutableStateFlow(false)
    val tapesLoadingComplete= _tapesLoading.asStateFlow()
    fun loadUserTapes() {

        viewModelScope.launch {
            try {
                auth.currentUser?.let {
                    val uid = it.uid
                    val docRef = db.collection("users").document(uid)
                    docRef.get()
                        .addOnSuccessListener { document ->

                            document.reference.collection(TAPES_NODE).get()
                                .addOnSuccessListener { querySnapshot ->
                                    for (document in querySnapshot) {
                                        //do something with the data
                                        tapes.add(Tapes(url=document.data["url"].toString()
                                            ,imageUrl=document.data["imageUrl"].toString()))
                                        println(document.data["url"].toString())
                                    }
                                }
                                .addOnCompleteListener {
                                    _tapesLoading.value = true
                                }
                        }
                }
            }
            catch(e : Exception) {
            }finally {

            }

            }
        }
    }
fun createVideoThumb(context: Context, uri: Uri): Bitmap? {
    try {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, uri)
        return mediaMetadataRetriever.frameAtTime
    } catch (ex: Exception) {
        Toast
            .makeText(context, "Error retrieving bitmap", Toast.LENGTH_SHORT)
            .show()
    }
    return null

}