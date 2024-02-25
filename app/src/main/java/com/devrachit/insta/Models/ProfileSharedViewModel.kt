package com.devrachit.insta.Models

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileSharedViewModel @Inject constructor() : ViewModel(){
    var email2 : String = ""
    var userName: String = ""
    var password: String = ""
    var emailVerified: Boolean = false
    var uid = ""
    var test=1

    private val _username= MutableLiveData<String>()
    var username : LiveData<String> =_username

    private val _email= MutableLiveData<String>("")
    var email : LiveData<String> =_email

    private val _following = MutableLiveData<Int>()
    val following : LiveData<Int> =_following

    private val _followers = MutableLiveData<Int>()
    val followers : LiveData<Int> =_followers

    private val _postCount = MutableLiveData<Int>()
    val postCount : LiveData<Int> =_postCount

    private val _profileImage = MutableLiveData<String>()
    val profileImage : LiveData<String> =_profileImage

    private val _test2= MutableLiveData<String>()
    val test2 : LiveData<String> =_test2

    private val bottomNavigationVisibility = MutableStateFlow(true)
    val bottomNavigationVisibilityLiveData= bottomNavigationVisibility.asStateFlow()

    private val userVerified = MutableLiveData(true)
    val userVerifiedLiveData : LiveData<Boolean> = userVerified

    private var bio =MutableLiveData<String>("")
    val bioLiveData: LiveData<String> = bio

    fun setBio(value: String) {
        bio.value = value
    }
    fun setUserVerified(value: Boolean) {
        userVerified.value = value
    }

    fun setBottomNavigationVisibility(value: Boolean) {
        bottomNavigationVisibility.value = value
    }

    fun setEmail23(value: String) {
        _test2.value = value
    }
    fun setUsername(value: String) {
        _username.value = value
    }
    fun setEmail(value: String) {
        _email.value = value
    }
    fun setFollowing(value: Int) {
        _following.value = value
    }
    fun setFollowers(value: Int) {
        _followers.value = value
    }
    fun setProfileImage(value: String) {
        _profileImage.value = value
    }
    fun setPostCount(value: Int) {
        _postCount.value = value
    }
    fun clearData() {
        email2 = ""
        userName = ""
        password = ""
    }

}