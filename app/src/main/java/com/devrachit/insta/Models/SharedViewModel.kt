package com.devrachit.insta.Models


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SharedViewModel  @Inject constructor() : ViewModel() {
    var email2 : MutableLiveData<String> = MutableLiveData()
    var email: String= ""
    var userName: String = ""
    var password: String = ""
    var emailVerified: Boolean = false
    var uid = ""

    fun clearData() {
        email = ""
        userName = ""
        password = ""
    }
}
