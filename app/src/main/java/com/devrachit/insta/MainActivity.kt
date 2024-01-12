package com.devrachit.insta

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devrachit.insta.screens.CheckYourMail
import com.devrachit.insta.ui.theme.InstaTheme
import com.devrachit.insta.screens.ChoiceScreen
import com.devrachit.insta.screens.DashboardScreen
import com.devrachit.insta.screens.LoginScreen
import com.devrachit.insta.screens.SignupScreen
import com.devrachit.insta.screens.SplashScreen
import com.devrachit.insta.viewModel.LCViewModel
import com.devrachit.insta.viewModel.LoginViewModel
import com.devrachit.insta.viewModel.VerifyEmailViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Screen(val route:String)
{
    object SplashScreen :Screen("SplashScreen")
    object LoginScreen:Screen("LoginScreen")
    object SignupScreen :Screen("SignupScreen")
    object ChoiceScreen :Screen("ChoiceScreen")
    object DashboardScreen :Screen("DashboardScreen")

    object CheckYourMail :Screen("CheckYourMail")

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
        NavHost(navController = navController, startDestination = com.devrachit.insta.Screen.ChoiceScreen.route){
            composable(com.devrachit.insta.Screen.SplashScreen.route){
                SplashScreen(navController = navController, viewModel = vm)
            }
            composable(com.devrachit.insta.Screen.LoginScreen.route){
                LoginScreen(navController = navController, viewModel = LoginViewModel)
            }
            composable(com.devrachit.insta.Screen.SignupScreen.route){
                SignupScreen(navController = navController,viewModel = vm)
            }
            composable(com.devrachit.insta.Screen.ChoiceScreen.route){
                ChoiceScreen(navController = navController, viewModel = vm)
            }
            composable(com.devrachit.insta.Screen.DashboardScreen.route){
                DashboardScreen(navController = navController,viewModel = vm)
            }
            composable(com.devrachit.insta.Screen.CheckYourMail.route){
                CheckYourMail(navController = navController,viewModel = VerifyEmailViewModel)
            }
        }
    }
}
