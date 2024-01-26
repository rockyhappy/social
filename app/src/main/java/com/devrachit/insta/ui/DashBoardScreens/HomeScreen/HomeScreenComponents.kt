package com.devrachit.insta.ui.DashBoardScreens.HomeScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devrachit.insta.ui.theme.primaryColor

@Composable
fun FAB(
    onFabClick:() -> Unit)
{
    FloatingActionButton(
        onClick = {
                  onFabClick.invoke()
        },
        containerColor = primaryColor,
        shape = CircleShape,
        modifier = Modifier
            .padding(bottom = 82.dp,end=16.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            tint = Color.Black,
            contentDescription = "Add Chat"
        )

    }
}

