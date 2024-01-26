package com.devrachit.insta


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devrachit.insta.ui.DashBoardScreens.ChatScreen.chatScreen
import com.devrachit.insta.ui.DashBoardScreens.HomeScreen.homeScreen
import com.devrachit.insta.ui.DashBoardScreens.MessagesScreen.messageScreen
import com.devrachit.insta.ui.DashBoardScreens.SearchScreen.searchScreen
import com.devrachit.insta.ui.DashBoardScreens.TapesScreen.tapesScreen
import com.devrachit.insta.ui.theme.InstaTheme
import com.devrachit.insta.ui.theme.black
import com.devrachit.insta.ui.theme.primaryColor
import com.devrachit.insta.ui.theme.transparent
import dagger.hilt.android.AndroidEntryPoint


data class BottomNavigationItem(
    val title :String,
    val selectedIcon:ImageVector,
    val unselectedIcon: ImageVector,
    val hasNotification: Boolean,
    val badgeCounter :Int?=null,
    val route: String
)

sealed class DashboardScreen(val route:String)
{
    object HomeScreen :DashboardScreen("HomeScreen")
    object SearchScreen:DashboardScreen("SearchScreen")
    object ReelsScreen :DashboardScreen("ReelsScreen")
    object ChatScreen :DashboardScreen("ShopScreen")
    object ProfileScreen :DashboardScreen("ProfileScreen")
}
@AndroidEntryPoint
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
                    val items= listOf(
                        BottomNavigationItem(
                            title = "Home",
                            selectedIcon = ImageVector.vectorResource(id = R.drawable.home_selected),
                            unselectedIcon = ImageVector.vectorResource(id = R.drawable.home),
                            hasNotification = false,
                            route = DashboardScreen.HomeScreen.route
                        ),
                        BottomNavigationItem(
                            title = "Search",
                            selectedIcon = ImageVector.vectorResource(id = R.drawable.search_selected),
                            unselectedIcon = ImageVector.vectorResource(id = R.drawable.search),
                            hasNotification = false,
                            route = DashboardScreen.SearchScreen.route
                        ),
                        BottomNavigationItem(
                            title = "Reels",
                            selectedIcon = ImageVector.vectorResource(id = R.drawable.play_selected),
                            unselectedIcon = ImageVector.vectorResource(id = R.drawable.play),
                            hasNotification = false,
                            route = DashboardScreen.ReelsScreen.route
                        ),
                        BottomNavigationItem(
                            title = "Shop",
                            selectedIcon = ImageVector.vectorResource(id = R.drawable.chat_selected),
                            unselectedIcon = ImageVector.vectorResource(id = R.drawable.chat),
                            hasNotification = false,
                            route = DashboardScreen.ChatScreen.route
                        ),
                        BottomNavigationItem(
                            title = "Profile",
                            selectedIcon = ImageVector.vectorResource(id = R.drawable.message_selected),
                            unselectedIcon = ImageVector.vectorResource(id = R.drawable.message),
                            hasNotification = false,
                            route = DashboardScreen.ProfileScreen.route
                        )
                    )
                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    val navController = rememberNavController()

                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        selectedItemIndex = items.indexOfFirst { it.route == destination.route }
                    }
                    Scaffold (
                        bottomBar = {
                            NavigationBar(
                                containerColor = black,
                            ) {
                                items.forEachIndexed { index, bottomNavigationItem ->
                                    NavigationBarItem(
                                        modifier= Modifier
                                            .fillMaxWidth()
                                            .height(56.dp)
                                            .align(CenterVertically)
                                        ,
                                        icon = {
//                                            BadgedBox(badge = {
//                                                if(bottomNavigationItem.hasNotification) {
//                                                    Text(text = bottomNavigationItem.badgeCounter.toString())
//                                                }
//                                            }) {
                                                Icon(
                                                    imageVector =
                                                    if (selectedItemIndex == index)
                                                        bottomNavigationItem.selectedIcon
                                                    else
                                                        bottomNavigationItem.unselectedIcon
                                                    ,
                                                    contentDescription =null,
                                                    modifier = Modifier
                                                        .width(24.dp)
                                                        .height(24.dp)

                                                )
//                                            }

                                        },
//                                        label = {
//                                            Text(text = bottomNavigationItem.title)
//                                        },
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(bottomNavigationItem.route)

                                        },
                                        colors = androidx.compose.material3.NavigationBarItemDefaults
                                            .colors(
                                                selectedIconColor = primaryColor,
                                                indicatorColor = transparent,
                                            )
                                    )
                                }
                            }
                        }
                    ){
                        println(it.toString())
                        NavHost(navController = navController, startDestination = DashboardScreen.HomeScreen.route){
                            composable(DashboardScreen.HomeScreen.route){
                                homeScreen(navController)
                            }
                            composable(DashboardScreen.SearchScreen.route){
                                searchScreen(navController)
                            }
                            composable(DashboardScreen.ReelsScreen.route){
                                tapesScreen(navController)
                            }
                            composable(DashboardScreen.ChatScreen.route){
                                chatScreen(navController)
                            }
                            composable(DashboardScreen.ProfileScreen.route){
                                messageScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}