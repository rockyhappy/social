package com.devrachit.insta.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.devrachit.insta.Constants.Constants
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.Screen
import com.devrachit.insta.ui.theme.errorColor
import com.devrachit.insta.ui.theme.gray
import com.devrachit.insta.util.ButtonImage
import com.devrachit.insta.util.CommonDivider
import com.devrachit.insta.util.errorFeild
import com.devrachit.insta.util.errorFeild2
import com.devrachit.insta.util.isValidEmail
import com.devrachit.insta.util.isValidPassword
import com.devrachit.insta.util.navigateToScreen
import com.devrachit.insta.viewModel.LCViewModel
import com.devrachit.insta.viewModel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
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

    var passwordIncorrectMessage="Incorrect Message"
    var emailIncorrectMessage="Incorrect Email"

    LaunchedEffect(viewModel.loginComplete.value) {
        if (viewModel.loginComplete.value) {
            if (viewModel.userEmailVerified.value) {
                navigateToScreen(
                    navController = navController,
                    route = Screen.DashboardScreen.route
                )
            } else {
                navigateToScreen(
                    navController = navController,
                    route = Screen.CheckYourMail.route
                )
            }
        }
    }
    val context = LocalContext.current
    val token = Constants.web_Client_Id
    val launcherNav = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        navController.navigate(Screen.DashboardScreen.route)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) {
        val task =
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    .getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener {task->
                        if (task.isSuccessful) {

                            val user = FirebaseAuth.getInstance().currentUser
                            user?.let {
                                val uid = it.uid.toString()
                                val email = it.email.toString()
                                val username = it.displayName.toString()
                                viewModel.updateUser(email, username,uid)
                            }

                            navigateToScreen(navController,Screen.DashboardScreen.route)
                        }
                    }
            }
            catch (e: ApiException) {
                Log.w("TAG", "GoogleSign in Failed", e)
            }
    }


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
                focusedBorderColor = if(viewModel.emailValid.value) primaryColor else errorColor,
                focusedLabelColor = if(viewModel.emailValid.value) primaryColor else errorColor,
                cursorColor = if(viewModel.emailValid.value) primaryColor else errorColor,
                unfocusedBorderColor = if(viewModel.emailValid.value) gray else errorColor,
                unfocusedLabelColor = if(viewModel.emailValid.value) gray else errorColor
            )
        )
        errorFeild2(emailIncorrectMessage,viewModel.emailValid.value)
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
                focusedBorderColor = if(viewModel.passwordValid.value) primaryColor else errorColor,
                focusedLabelColor = if (viewModel.passwordValid.value) primaryColor else errorColor,
                cursorColor = if (viewModel.passwordValid.value) primaryColor else errorColor,
                unfocusedBorderColor = if (viewModel.passwordValid.value) gray else errorColor,
                unfocusedLabelColor = if (viewModel.passwordValid.value) gray else errorColor
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
        errorFeild(passwordIncorrectMessage,viewModel.passwordValid.value)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 6.dp, end = 24.dp)
                ,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Forgot Password?",
                fontSize = 14.sp,
                fontFamily = customFontFamily,
                modifier = Modifier.clickable {  })
        }
        Button(
            onClick = {
                if(viewModel.loginCheck(emailState.value.text.trim(),passwordState.value.text)
                    && isValidEmail(emailState.value.text)
                    && isValidPassword(passwordState.value.text)
                    )
                {
                   viewModel.loginUser(emailState.value.text,passwordState.value.text)
//                    if(viewModel.userEmailVerified.value){
//                        navigateToScreen(
//                            navController = navController,
//                            route = Screen.DashboardScreen.route
//                        )
//                    }
//                    else{
//                        navigateToScreen(
//                            navController = navController,
//                            route = Screen.CheckYourMail.route
//                        )
//                    }
                }
            },
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
                val gso = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)

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