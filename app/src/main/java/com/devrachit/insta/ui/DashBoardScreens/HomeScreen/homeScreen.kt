package com.devrachit.insta.ui.DashBoardScreens.HomeScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.devrachit.insta.ui.theme.primaryColor

@Composable
fun homeScreen(navController: NavController, viewModel: ProfileSharedViewModel) {
    val onFabClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the FAB is clicked
    }
    LaunchedEffect(key1 = true)
    {
        viewModel.setEmail23("123")
        println(viewModel.test2.value)
    }
    Scaffold(
        floatingActionButton = {
            FAB(onFabClick = onFabClick)
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        println(it.toString())
    }
}