package com.devrachit.insta.ui.DashBoardScreens.HomeScreen

import android.widget.Toast
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.insta.DashboardScreen
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.devrachit.insta.Screen
import com.devrachit.insta.ui.theme.grayShade2
import com.devrachit.insta.util.navigateToScreen
import kotlinx.coroutines.delay

@Composable
fun homeScreen(navController: NavController, sharedViewModel: ProfileSharedViewModel) {
    val context = LocalContext.current

    val onFabClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the FAB is clicked


    }

    val onProfileClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the profile icon is clicked
        navigateToScreen(
            navController = navController,
            route = DashboardScreen.ProfileScreenMain.route
        )


    }

    val onNotificationClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the notification icon is clicked
    }

    val onSettingsClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the settings icon is clicked
    }

    val viewModel = hiltViewModel<homeScreenViewModel>()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()
    val followers by viewModel.followers.collectAsStateWithLifecycle()
    val following by viewModel.following.collectAsStateWithLifecycle()
    val postCount by viewModel.postCount.collectAsStateWithLifecycle()
    val profileImage by viewModel.profileImage.collectAsStateWithLifecycle()
    sharedViewModel.setBottomNavigationVisibility(true)

    LaunchedEffect(key1 = true)
    {
        viewModel.getUserData(sharedViewModel)
    }
    sharedViewModel.setUsername(username.toString())
    sharedViewModel.setFollowers(followers)
    sharedViewModel.setFollowing(following)
    sharedViewModel.setPostCount(postCount)
    sharedViewModel.setProfileImage(profileImage.toString())
    sharedViewModel.setEmail(email.toString())

    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val inProgress by viewModel.inProgress.collectAsStateWithLifecycle()

    if (!loading && !inProgress) {
        Text("Loading")
    } else {
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
                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/twingle-c1acb.appspot.com/o/avatar.png?alt=media&token=cc768fc6-57c1-4326-a874-4f0fd3bbe5de",
                    userName = sharedViewModel.username.value.toString()
                )
            },
            containerColor = grayShade2
        ) {
            println(it.toString())
        }
    }
}