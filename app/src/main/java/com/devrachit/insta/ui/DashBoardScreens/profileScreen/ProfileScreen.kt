package com.devrachit.insta.ui.DashBoardScreens.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.Models.ProfileSharedViewModel
import com.devrachit.insta.R
import com.devrachit.insta.ui.theme.grayShade2
import com.devrachit.insta.ui.theme.grayShade4
import com.devrachit.insta.ui.theme.grayShade5
import com.devrachit.insta.ui.theme.primaryColor
import kotlin.random.Random

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

    val onBackClick: () -> Unit = {
        navController.popBackStack()
    }
    val onEditProfileClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the Edit Profile button is clicked
    }
    val onShareProfileClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the Share Profile button is clicked
    }
    val onMenuClick: () -> Unit = {
        //This is a lambda function
        //This is the function which will be called when the Menu button is clicked
    }

    var selected by remember { mutableStateOf(1) }
    val loadingComplete = viewModel.loadingComplete.collectAsStateWithLifecycle().value
    val tapesLoadingComplete = viewModel.tapesLoadingComplete.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = true) {
//        Toast.makeText(context, sharedViewModel., Toast.LENGTH_SHORT).show()
        viewModel.loadUserPosts()
        viewModel.loadUserTapes()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = grayShade2)
//            .verticalScroll(rememberScrollState())
        ,
    )
    {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .background(color = grayShade2),
//                .nestedScroll(rememberNestedScrollInteropConnection())
//                .verticalScroll(rememberScrollState())
            horizontalAlignment = Alignment.CenterHorizontally,


            ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularIconButton(
                    icon = R.drawable.up_1,
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = { onBackClick.invoke() }
                )
                ImageExample(
                    image = sharedViewModel.profileImage.value.toString(),
                    modifier = Modifier
                        .padding(top = 64.dp)
                )
                CircularIconButton(
                    icon = R.drawable.menu_meatballs,
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = { onMenuClick.invoke() }
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

                grayButton(onClick = { onShareProfileClick.invoke() }, text = "Share Profile")
            }


            /**
             * This is the navigation Row
             * with 4 button Photos Tapes Stories Tagged
             */

            ProfileNavigation(selected = selected, onItemSelected = { selected = it })
        }




        when (selected) {
            1 -> {
                if (loadingComplete) {
                    StaggeredPostList(items = viewModel.posts)
                }

            }

            2 -> {
                if (tapesLoadingComplete) {
                    StaggeredTapesList(items = viewModel.tapes, context)
                }


            }

            3 -> {

            }

            4 -> {

            }
        }
    }
}


