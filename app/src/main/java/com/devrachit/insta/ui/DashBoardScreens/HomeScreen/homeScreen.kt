package com.devrachit.insta.ui.DashBoardScreens.HomeScreen

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.devrachit.insta.Models.ProfileSharedViewModel

@Composable
fun homeScreen(navController: NavController, viewModel: ProfileSharedViewModel) {
    val onFabClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the FAB is clicked
    }

    val onProfileClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the profile icon is clicked
    }

    val onNotificationClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the notification icon is clicked
    }

    val onSettingsClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the settings icon is clicked
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
        topBar = {
            topBar(
                onProfileClick = onProfileClick,
                onNotificationClick = onNotificationClick,
                onSettingsClick = onSettingsClick,
//                imageUrl = viewModel.profileImage.value.toString()
                imageUrl="https://firebasestorage.googleapis.com/v0/b/twingle-c1acb.appspot.com/o/avatar.png?alt=media&token=cc768fc6-57c1-4326-a874-4f0fd3bbe5de",
                userName= viewModel.username1.value.toString()
            )
        },
    ) {
        println(it.toString())
    }
}