package com.devrachit.insta.ui.DashBoardScreens.HomeScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.insta.DashboardScreen
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.devrachit.insta.R
import com.devrachit.insta.Screen
import com.devrachit.insta.ui.theme.grayShade2
import com.devrachit.insta.ui.theme.whiteShade2
import com.devrachit.insta.util.navigateToScreen
import kotlinx.coroutines.delay

@Composable
fun homeScreen(navController: NavController, sharedViewModel: ProfileSharedViewModel) {
    val context = LocalContext.current
    var expand by remember{ mutableStateOf(false) }
    val onFabClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the FAB is clicked
        expand=!expand

    }

    val onProfileClick: () -> Unit = {
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

    val onPlayClick:()->Unit={
        Toast.makeText(context, "Play Clicked", Toast.LENGTH_SHORT).show()
    }

    val onImageClick:()->Unit={
        Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show()
    }

    val onVideoClick:()->Unit={
        Toast.makeText(context, "Video Clicked", Toast.LENGTH_SHORT).show()
    }

    val viewModel = hiltViewModel<homeScreenViewModel>()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()
    val followers by viewModel.followers.collectAsStateWithLifecycle()
    val following by viewModel.following.collectAsStateWithLifecycle()
    val postCount by viewModel.postCount.collectAsStateWithLifecycle()
    val profileImage by viewModel.profileImage.collectAsStateWithLifecycle()
    val bio by viewModel.bio.collectAsStateWithLifecycle()
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
    sharedViewModel.setBio(bio.toString())

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
                    imageUrl = sharedViewModel.profileImage.value.toString(),
                    userName = sharedViewModel.username.value.toString()
                )
            },
            containerColor = grayShade2
        ) {
            Box(
                modifier= Modifier
                    .fillMaxSize()
                    .alpha(if (expand) 0.1f else 1f)
                    .background(grayShade2)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { expand = false }
                        )
                    }
            )
            {
                Image(
                    painter=painterResource(id = R.drawable.home),
                    modifier = Modifier.size(800.dp),
                    contentDescription = "Home Image"

                )

            }
            AnimatedVisibility(
                visible = expand,
                enter = expandIn(expandFrom = Alignment.BottomEnd, animationSpec = spring()),
                exit = shrinkOut(shrinkTowards = Alignment.TopStart, animationSpec = spring()),
                modifier = Modifier.animateContentSize()
            ) {
                Column(
                    modifier=Modifier.fillMaxSize().padding(bottom=160.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Bottom
                ) {

                    CircularIconButton2(
                        color = whiteShade2,
                        icon = R.drawable.video_2,
                        onClick = onPlayClick,
                        modifier = Modifier.padding(bottom=16.dp,end=40.dp),
                        text="Go Live"
                    )
                    CircularIconButton2(
                        color = whiteShade2,
                        icon = R.drawable.image,
                        onClick = onImageClick,
                        modifier = Modifier.padding(bottom=16.dp,end=40.dp),
                        text="Photo"
                    )
                    CircularIconButton2(
                        color = whiteShade2,
                        icon = R.drawable.play2,
                        onClick = onVideoClick,
                        modifier = Modifier.padding(bottom =16.dp,end=40.dp),
                        text="Tapes"
                    )

                }
            }
            println(it.toString())
        }
    }
}