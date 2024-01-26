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
    private val _test2= MutableLiveData<String>()
    val test2 : LiveData<String> =_test2

    fun setEmail23(value: String) {
        _test2.value = value
    }
    fun clearData() {
        email2 = ""
        userName = ""
        password = ""
    }
}