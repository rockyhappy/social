package com.devrachit.insta.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val username1 : LiveData<String> =_username

    private val _email= MutableLiveData<String>()
    val email : LiveData<String> =_email

    private val _following = MutableLiveData<Int>()
    val following1 : LiveData<Int> =_following

    private val _followers = MutableLiveData<Int>()
    val followers1 : LiveData<Int> =_followers

    private val _profileImage = MutableLiveData<String>()
    val profileImage : LiveData<String> =_profileImage

    private val _test2= MutableLiveData<String>()
    val test2 : LiveData<String> =_test2

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
    fun clearData() {
        email2 = ""
        userName = ""
        password = ""
    }

}