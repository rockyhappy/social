package com.devrachit.insta.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.devrachit.insta.R
import com.devrachit.insta.ui.theme.primaryColor
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.painter.Painter
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.Screen
import com.devrachit.insta.ui.theme.gray
import com.devrachit.insta.util.SelfRunningImageCarousel
import com.devrachit.insta.util.navigateToScreen
import com.devrachit.insta.viewModel.LCViewModel
import kotlinx.coroutines.launch

@Composable
fun ChoiceScreen(navController: NavController, viewModel: LCViewModel) {
    val images = listOf(
        painterResource(id = R.drawable.image1),
        painterResource(id = R.drawable.image2),
        painterResource(id = R.drawable.image3),
        painterResource(id = R.drawable.image4)
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 48.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    .padding(start = 4.dp)
                    .wrapContentHeight()
                    .wrapContentWidth(),
                fontFamily = customFontFamily
            )
        }

        SelfRunningImageCarousel(images = images)
        Button(
            onClick = {
                navController.navigate(Screen.SignupScreen.route) {
                    popUpTo(Screen.ChoiceScreen.route) { inclusive = false }
                }
                },
                modifier = Modifier
                    .padding(top = 96.dp, start = 24.dp, end = 24.dp)
                    .height(54.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.Black
                )

                ) {
                Text("Get Started", fontFamily = customFontFamily)
            }
                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route) {
                            popUpTo(Screen.ChoiceScreen.route) { inclusive = false }
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                        .height(54.dp)
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, gray),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )

                ) {
                    Text("Login", color = Color.White, fontFamily = customFontFamily)
                }
            }

    }