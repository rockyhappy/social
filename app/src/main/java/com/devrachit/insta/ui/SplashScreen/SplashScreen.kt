package com.devrachit.insta.ui.SplashScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.insta.Constants.Constants
import com.devrachit.insta.Dashboard
import com.devrachit.insta.R
import com.devrachit.insta.Screen
import com.devrachit.insta.util.navigateToScreen
import com.devrachit.insta.ui.SplashScreen.SplashScreenViewModel


@Composable
fun SplashScreen(navController: NavController, viewModel: SplashScreenViewModel) {
    val loading by  viewModel.inProgress.collectAsStateWithLifecycle()
    val load by viewModel.load.collectAsStateWithLifecycle()

    val context= LocalContext.current
    LaunchedEffect(load){
        if(viewModel.checkUserLoggedIn()
//            && viewModel.sharedViewModel.emailVerified
            ){

//            navigateToScreen(navController = navController, route = Screen.DashboardScreen.route)

            val intent = Intent(context, Dashboard::class.java)
            context.startActivity(intent)
        }
        else{
            navigateToScreen(navController = navController, route = Screen.ChoiceScreen.route)
        }
    }

    Column(
        modifier= Modifier
            .fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        )
        {
            Image(
                painter = painterResource(id = R.drawable.logo),
                modifier = Modifier
                    .height(42.dp)
                    .width(49.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Twingle",
                color = Color.White,
                fontSize = 40.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .wrapContentHeight()
                    .wrapContentWidth(),
                fontFamily = Constants.customFontFamily
            )
        }

    }
}