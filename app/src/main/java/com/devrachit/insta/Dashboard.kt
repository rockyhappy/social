package com.devrachit.insta

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.devrachit.insta.ui.theme.InstaTheme


data class BottomNavigationItem(
    val title :String,
    val selectedIcon:ImageVector,
    val unselectedIcon: ImageVector,
    val hasNotification: Boolean,
    val badgeCounter :Int?=null
)
class Dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor= Color.BLACK
            window.navigationBarColor= Color.BLACK
            InstaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold (
                        bottomBar = {
                            NavigationBar {

                            }
                        }
                    ){

                    }
                }
            }
        }
    }
}