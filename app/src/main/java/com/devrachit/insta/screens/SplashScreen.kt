package com.devrachit.insta.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.devrachit.insta.viewModel.LCViewModel


@Composable
fun SplashScreen(navController: NavController, viewModel: LCViewModel) {
    Text(text = "HIHI", color = Color.White)
}