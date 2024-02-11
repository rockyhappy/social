package com.devrachit.insta.ui.DashBoardScreens.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.devrachit.insta.R
import com.devrachit.insta.ui.theme.grayShade2
import com.devrachit.insta.ui.theme.grayShade4
import com.devrachit.insta.ui.theme.grayShade5

@Composable
fun profileScreen(
    navController: NavController,
    sharedViewModel: ProfileSharedViewModel
) {
    sharedViewModel.setBottomNavigationVisibility(false)
    val context = LocalContext.current
    val viewModel = hiltViewModel<ProfileScreenViewModel>()
    val userVerifiedData = sharedViewModel.userVerifiedLiveData
    val bio = sharedViewModel.bioLiveData
    val followers = sharedViewModel.followers.value
    val following = sharedViewModel.following.value
    val postCount = sharedViewModel.postCount.value

    val onBackClick : () -> Unit = {
        navController.popBackStack()
    }
    val onEditProfileClick : () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the Edit Profile button is clicked
    }
    val onShareProfileClick : () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the Share Profile button is clicked
    }
    val onMenuClick : () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the Menu button is clicked
    }
    LaunchedEffect(key1 = true) {
//        Toast.makeText(context, sharedViewModel., Toast.LENGTH_SHORT).show()
    }
    Box(
        modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = grayShade2),
    )
    {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .background(color = grayShade2),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularIconButton(
                    icon = R.drawable.up_1,
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = {onBackClick.invoke()}
                )
                ImageExample(
                    image = sharedViewModel.profileImage.value.toString(),
                    modifier = Modifier
                        .padding(top = 64.dp)
                )
                CircularIconButton(
                    icon = R.drawable.menu_meatballs,
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = {onMenuClick.invoke()}
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(
                    text = sharedViewModel.username.value.toString(),
                    fontSize = 24.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                    color = Color.White
                )
                if (userVerifiedData.value == true) {
                    Image(
                        painter = painterResource(id = R.drawable.verified),
                        contentDescription = "Verified",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(24.dp)
                    )
                }

            }
            Text(
                text = bio.value.toString(),
                fontSize = 14.sp,
                fontFamily = customFontFamily,
                modifier = Modifier.padding(top = 2.dp),
                color = grayShade5
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                )
                {
                    Text(
                        text = postCount.toString(),
                        fontSize = 20.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp),
                        color = Color.White
                    )
                    Text(
                        text = "Posts",
                        fontSize = 14.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = grayShade4
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                )
                {
                    Text(
                        text = followers.toString(),
                        fontSize = 20.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp),
                        color = Color.White
                    )
                    Text(
                        text = "Followers",
                        fontSize = 14.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 1.dp),
                        color = grayShade4
                    )

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                )
                {
                    Text(
                        text = following.toString(),
                        fontSize = 20.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp),
                        color = Color.White
                    )
                    Text(
                        text = "Following",
                        fontSize = 14.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 1.dp),
                        color = grayShade4
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
            {
                grayButton(onClick = { onEditProfileClick.invoke() }, text = "Edit Profile")

                grayButton(onClick = { onShareProfileClick.invoke()}, text = "Share Profile")
            }

        }
    }
}