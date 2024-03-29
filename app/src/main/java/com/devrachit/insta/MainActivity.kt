package com.devrachit.insta

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devrachit.insta.ui.VerifyEmailScreen.CheckYourMail
import com.devrachit.insta.ui.theme.InstaTheme
import com.devrachit.insta.ui.ChoiceScreen.ChoiceScreen
import com.devrachit.insta.ui.DashBoardScreens.DashboardScreen
import com.devrachit.insta.ui.ForgotPasswordScreen.ForgotPasswordScreen
import com.devrachit.insta.ui.ForgotPasswordScreen.ForgotPasswordViewModel
import com.devrachit.insta.ui.LoginScreen.LoginScreen
import com.devrachit.insta.ui.SignUpScreen.SignupScreen
import com.devrachit.insta.ui.SplashScreen.SplashScreen
import com.devrachit.insta.ui.SignUpScreen.LCViewModel
import com.devrachit.insta.ui.LoginScreen.LoginViewModel
import com.devrachit.insta.ui.SplashScreen.SplashScreenViewModel
import com.devrachit.insta.ui.VerifyEmailScreen.VerifyEmailViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Screen(val route:String)
{
    object SplashScreen :Screen("SplashScreen")
    object LoginScreen:Screen("LoginScreen")
    object SignupScreen :Screen("SignupScreen")
    object ChoiceScreen :Screen("ChoiceScreen")
    object DashboardScreen :Screen("DashboardScreen")
    object CheckYourMail :Screen("CheckYourMail")
    object ForgotPasswordScreen :Screen("ForgotPasswordScreen")

}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor= Color.BLACK
            window.navigationBarColor=Color.BLACK
            InstaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TwingleNavigation()
                }
            }
        }
    }
    @Composable
    fun TwingleNavigation(){
        val navController = rememberNavController()
        val vm = hiltViewModel<LCViewModel>()
        val LoginViewModel= hiltViewModel<LoginViewModel>()
        val VerifyEmailViewModel= hiltViewModel<VerifyEmailViewModel>()
        val splashScreenViewModel= hiltViewModel<SplashScreenViewModel>()
        val forgotPasswordViewModel= hiltViewModel<ForgotPasswordViewModel>()
        NavHost(navController = navController, startDestination = com.devrachit.insta.Screen.SplashScreen.route){
            composable(com.devrachit.insta.Screen.SplashScreen.route){
                SplashScreen(navController = navController, viewModel = splashScreenViewModel)
            }
            composable(com.devrachit.insta.Screen.LoginScreen.route){
                LoginScreen(navController = navController, viewModel = LoginViewModel)
            }
            composable(com.devrachit.insta.Screen.SignupScreen.route){
                SignupScreen(navController = navController,viewModel = vm)
            }
            composable(com.devrachit.insta.Screen.ChoiceScreen.route){
                ChoiceScreen(navController = navController)
            }
            composable(com.devrachit.insta.Screen.DashboardScreen.route){
                DashboardScreen(navController = navController,viewModel = vm)
            }
            composable(com.devrachit.insta.Screen.CheckYourMail.route){
                CheckYourMail(navController = navController,viewModel = VerifyEmailViewModel)
            }
            composable(com.devrachit.insta.Screen.ForgotPasswordScreen.route){
                ForgotPasswordScreen(navController = navController,viewModel = forgotPasswordViewModel)
            }
        }
    }
}
