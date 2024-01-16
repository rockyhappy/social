package com.devrachit.insta.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.devrachit.insta.ui.SignUpScreen.LCViewModel

@Composable
fun DashboardScreen(navController: NavController,viewModel: LCViewModel) {
    Text(text = "Dashboard", color = androidx.compose.ui.graphics.Color.White)
}