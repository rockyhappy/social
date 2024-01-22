package com.devrachit.insta.ui.ForgotPasswordScreen

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.insta.Constants.Constants
import com.devrachit.insta.R
import com.devrachit.insta.Screen
import com.devrachit.insta.ui.theme.errorColor
import com.devrachit.insta.ui.theme.lightGray
import com.devrachit.insta.ui.theme.primaryColor
import com.devrachit.insta.ui.theme.successColor
import com.devrachit.insta.util.navigateToScreen
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(navController: NavController, viewModel: ForgotPasswordViewModel) {
    if (viewModel.userEmailVerified.value) {
        navigateToScreen(navController = navController, route = Screen.DashboardScreen.route)
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
            text = Constants.email,
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
        CountdownTimerWithReset(viewModel = viewModel)
        Text(
            text = if (loading) "Password mail not send yet" else "Password Reset Mail sent Successfully",
            color = if (loading) errorColor else successColor,
            fontSize = 20.sp,
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 72.dp)
                .wrapContentWidth(),
            fontFamily = Constants.customFontFamily,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                navigateToScreen(
                    navController = navController,
                    route = Screen.LoginScreen.route
                )
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
                    "Login",
                    fontFamily = Constants.customFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}


@Composable
fun CountdownTimerWithReset(viewModel: ForgotPasswordViewModel) {
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
                text = "Resend Mail",
                fontFamily = Constants.customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 28.dp)
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .clickable {
                        resetTimer()
                        viewModel.sendPasswordResetEmail(Constants.email)
                    },
            )
        } else {
            Text(
                text = "Resend mail in : $timeLeft Seconds",
                fontFamily = Constants.customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 28.dp)
                    .wrapContentHeight()
                    .wrapContentWidth()
            )
        }

    }
}
