package com.devrachit.insta.ui.VerifyEmailScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.devrachit.insta.Constants.Constants
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.Constants.Constants.Companion.email
import com.devrachit.insta.Constants.Constants.Companion.userVerify
import com.devrachit.insta.Dashboard
import com.devrachit.insta.R
import com.devrachit.insta.Screen
import com.devrachit.insta.ui.theme.errorColor
import com.devrachit.insta.ui.theme.lightGray
import com.devrachit.insta.ui.theme.primaryColor
import com.devrachit.insta.ui.theme.successColor
import com.devrachit.insta.util.navigateToScreen
import com.devrachit.insta.ui.VerifyEmailScreen.VerifyEmailViewModel
import kotlinx.coroutines.delay

@Composable
fun CheckYourMail(navController: NavController, viewModel: VerifyEmailViewModel) {

    if (viewModel.userEmailVerified.value) {
//        navigateToScreen(navController = navController, route = Screen.DashboardScreen.route)
        val context= LocalContext.current
        val intent = Intent(context, Dashboard::class.java)
        context.startActivity(intent)
    }
    val context= LocalContext.current
    LaunchedEffect(key1 =true) {
        if(viewModel.checkEmailVerification()) {
            val intent = Intent(context, Dashboard::class.java)
            context.startActivity(intent)
        }
    }
    val loading by viewModel.inProgress.collectAsStateWithLifecycle()
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
            text = email,
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
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .wrapContentHeight()
                .wrapContentWidth(),
            fontFamily = Constants.customFontFamily,
            textAlign = TextAlign.Center
        )
        CountdownTimerWithReset(viewModel =viewModel)
        Text(
            text = if (!viewModel.userEmailVerified.value) "Email Not Verified" else "Email Verified",
            color = if (!viewModel.userEmailVerified.value) errorColor else successColor,
            fontSize = 20.sp,
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 72.dp)
                .wrapContentWidth(),
            fontFamily = Constants.customFontFamily
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                println(viewModel.sharedViewModel.email.toString())
                viewModel.checkEmailVerification()
            },
            modifier = Modifier
                .padding(bottom = 24.dp)
                .height(54.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = Color.Black
            ),
            enabled = !loading
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    color = primaryColor
                )
            } else {
                Text(
                    "Proceed",
                    fontFamily = Constants.customFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}

@Composable
fun CountdownTimerWithReset(viewModel: VerifyEmailViewModel) {
    var timeLeft by remember { mutableStateOf(60) }
    var isPaused by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = timeLeft, key2 = isPaused) {
        while (timeLeft > 0 && !isPaused) {
            delay(1000L)
            timeLeft--
        }
    }

    fun resetTimer() {
        timeLeft = 60
        isPaused = false
    }

    Column {
        if (timeLeft == 0) {
            Text(
                text = "Resend Mail" ,
                fontFamily= customFontFamily,
                fontWeight= FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 28.dp)
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .clickable {
                        resetTimer()
                        viewModel.sendEmailVerification()
                    },
            )
        } else {
            Text(text = "Resend mail in : $timeLeft Seconds",
                fontFamily= customFontFamily,
                fontWeight= FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 28.dp)
                    .wrapContentHeight()
                    .wrapContentWidth()
            )
        }

    }
}
