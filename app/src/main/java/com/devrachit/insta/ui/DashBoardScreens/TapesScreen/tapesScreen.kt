package com.devrachit.insta.ui.DashBoardScreens.TapesScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.devrachit.insta.Models.ProfileSharedViewModel

@Composable
fun tapesScreen(navController: NavController, viewModel: ProfileSharedViewModel) {

    LaunchedEffect(key1=true)
    {
//        viewModel.setEmail23("123")
        println(viewModel.test2.value)
    }

}