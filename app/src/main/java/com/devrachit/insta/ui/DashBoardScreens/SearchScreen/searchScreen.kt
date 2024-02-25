package com.devrachit.insta.ui.DashBoardScreens.SearchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.DashboardScreen
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.devrachit.insta.ui.DashBoardScreens.profileScreen.ProfileScreenViewModel
import com.devrachit.insta.ui.theme.black
import com.devrachit.insta.ui.theme.grayShade1
import com.devrachit.insta.ui.theme.grayShade2
import com.devrachit.insta.util.navigateToScreen

@Composable
fun searchScreen(navController: NavController, sharedViewModel: ProfileSharedViewModel) {
    sharedViewModel.setBottomNavigationVisibility(true)
    val context = LocalContext.current
    val viewModel = hiltViewModel<SearchScreenViewModel>()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val persons by viewModel.persons.collectAsState()

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


    Scaffold(
        topBar = {
            topBar(
                onProfileClick = onProfileClick,
                onNotificationClick = onNotificationClick,
                onSettingsClick = onSettingsClick,
                imageUrl = sharedViewModel.profileImage.value.toString(),
                userName = sharedViewModel.username.value.toString(),
                searchText = searchText,
                onSearchTextChange = {
                    viewModel.onSearchTextChange(it)
                },
                isSearching = isSearching,
                persons = persons
            )
        }
    ) {

        if (searchResults.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .background(color = grayShade2)
                    .fillMaxSize()
                    .padding(horizontal=16.dp)
            )
            {


                Text(
                    text = "Search Results",
                    color = Color.White,
                    modifier = Modifier
                        .padding(top=88.dp)
                        ,
                    fontSize = 20.sp,
                    fontFamily = customFontFamily
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 128.dp) // Adjust padding as needed
                ) {
                    items(searchResults.size) {
                        Row(
                            modifier = Modifier
                                .background(color = grayShade2)
                                .padding(vertical = 16.dp)
                        ) {
                            ImageExample(image = searchResults[it].profilePic)
                            Text(text = searchResults[it].userName, color = Color.White)
                        }
                    }
                }
            }
        }
        println("padding value${it.toString()}")
    }
}