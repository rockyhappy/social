package com.devrachit.insta.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.devrachit.insta.R
import com.devrachit.insta.ui.theme.primaryColor
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.Screen
import com.devrachit.insta.ui.theme.errorColor
import com.devrachit.insta.ui.theme.gray
import com.devrachit.insta.util.ButtonImage
import com.devrachit.insta.util.CommonDivider
import com.devrachit.insta.util.errorFeild
import com.devrachit.insta.util.navigateToScreen
import com.devrachit.insta.viewModel.LCViewModel


@Composable
fun LoginScreen(navController: NavController, viewModel: LCViewModel) {
    val emailState = remember {
        mutableStateOf(TextFieldValue())
    }
    val passwordState = remember {
        mutableStateOf(TextFieldValue())
    }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val visualTransformation: VisualTransformation =
        if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation()

    val scrollState = rememberScrollState()

    val h=viewModel.emailValid.value
    var passwordIncorrectMessage="Incorrect Message"
    var emailIncorrectMessage="Incorrect Email"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            modifier = Modifier
                .padding(top = 48.dp)
                .height(42.dp)
                .width(49.dp),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Welcome Back!",
            color = Color.White,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(top = 24.dp)
                .wrapContentHeight()
                .wrapContentWidth(),
            fontFamily = customFontFamily
        )
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 49.dp)
                .fillMaxWidth(),
            label = {
                Text(text = "Enter Email", fontFamily = customFontFamily)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                focusedLabelColor = primaryColor,
                cursorColor = primaryColor,
                unfocusedBorderColor = if(viewModel.emailValid.value) gray else errorColor
            )
        )
        errorFeild(emailIncorrectMessage)
        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = visualTransformation,
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                .fillMaxWidth(),
            label = {
                Text(text = "Enter Password", fontFamily = customFontFamily)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                focusedLabelColor = primaryColor,
                cursorColor = primaryColor,
                unfocusedBorderColor = gray
            ),
            trailingIcon = {
                val image = if (passwordVisibility)
                    painterResource(id = R.drawable.eye)
                else painterResource(id = R.drawable.slash_eye)
                val description = if (passwordVisibility) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(painter = image, description)
                }
            }
        )
        errorFeild(passwordIncorrectMessage)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 6.dp, end = 24.dp)
                .clickable {

                },
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Forgot Password?", fontSize = 14.sp, fontFamily = customFontFamily)
        }
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .height(54.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = Color.Black
            )

        ) {
            Text("Continue", fontFamily = customFontFamily)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            CommonDivider()
            Text(
                text = "or",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 7.dp),
                fontFamily = customFontFamily
            )
            CommonDivider()
        }
        OutlinedButton(
            onClick = {
                navigateToScreen(
                    navController = navController,
                    route = Screen.LoginScreen.route
                )
            },
            modifier = Modifier
                .padding(top = 32.dp, start = 24.dp, end = 24.dp)
                .height(54.dp)
                .fillMaxWidth(),
            border = BorderStroke(1.dp, gray),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent
            )

        ) {
            ButtonImage(painter = painterResource(id = R.drawable.google))
            Text(
                "Continue With Google",
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp),
                fontFamily = customFontFamily
            )
        }
        OutlinedButton(
            onClick = {
                navigateToScreen(
                    navController = navController,
                    route = Screen.LoginScreen.route
                )
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
            ButtonImage(painter = painterResource(id = R.drawable.facebook))
            Text(
                "Continue With Facebook",
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp),
                fontFamily = customFontFamily
            )
        }
        Row(
            modifier = Modifier
                .padding(42.dp)
        ) {
            Text(text = "Don't have an Account? ", fontFamily = customFontFamily)
            Text(text = "SignUp", fontWeight = FontWeight.Bold, fontFamily = customFontFamily)
        }
    }

}