package com.devrachit.insta.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devrachit.insta.Constants.Constants
import com.devrachit.insta.R
import com.devrachit.insta.ui.theme.lightGray
import com.devrachit.insta.viewModel.LCViewModel

@Composable
fun CheckYourMail(navController: NavController,viewModel: LCViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            modifier = Modifier
                .padding(top = 32.dp)
                .height(42.dp)
                .width(49.dp),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Check Your Mail",
            color = Color.White,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(top = 24.dp)
                .wrapContentHeight()
                .wrapContentWidth(),
            fontFamily = Constants.customFontFamily
        )
        Text(
            text = viewModel.emailData,
            color = lightGray,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .wrapContentHeight()
                .wrapContentWidth(),
            fontFamily = Constants.customFontFamily
        )
        Text(
            text = "A link has been sent to your mail, kindly click on that link to proceed further.",
            color = lightGray,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .wrapContentHeight()
                .wrapContentWidth(),
            fontFamily = Constants.customFontFamily
        )
    }

}