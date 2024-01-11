package com.devrachit.insta.viewModel


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SharedViewModel  @Inject constructor() : ViewModel() {
    var email: String = ""
    var userName: String = ""
    var password: String = ""

    fun clearData() {
        email = ""
        userName = ""
        password = ""
    }
}
