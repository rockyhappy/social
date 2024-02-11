package com.devrachit.insta.ui.DashBoardScreens.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.R
import com.devrachit.insta.ui.theme.gray
import com.devrachit.insta.ui.theme.grayShade1
import com.devrachit.insta.ui.theme.grayShade2
import com.devrachit.insta.ui.theme.grayShade3
import com.devrachit.insta.ui.theme.primaryColor
import com.devrachit.insta.util.clickableWithoutRipple

@Composable
fun FAB(
    onFabClick: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClick.invoke()
        },
        containerColor = primaryColor,
        shape = CircleShape,
        modifier = Modifier
            .padding(bottom = 82.dp, end = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            tint = Color.Black,
            contentDescription = "Add Chat"
        )

    }
}

@Composable
fun topBar(
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit,
    imageUrl: String = "",
    userName: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(grayShade2),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

    )
    {
        ImageExample(
            imageUrl,
            modifier = Modifier
                .padding(start = 16.dp)
                .clickableWithoutRipple(onProfileClick)
        )

        Column {
            Text(
                text = "Good Morning",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clickableWithoutRipple(onProfileClick),
                fontFamily = customFontFamily,
                fontSize = 12.sp,
                color = gray
            )
            Text(
                text = userName,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clickableWithoutRipple(onProfileClick),
                fontFamily = customFontFamily,
                fontSize = 12.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = grayShade3
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        CircularIconButton(
            R.drawable.notification_1,
            modifier = Modifier.padding(end = 16.dp),
            onClick = onNotificationClick
        )
        CircularIconButton(
            R.drawable.setting,
            modifier = Modifier.padding(end = 16.dp),
            onClick = onSettingsClick
        )

    }
}

@Composable
fun ImageExample(image: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .then(
                modifier
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = "Round Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape)
//                .border(0.dp, primaryColor, CircleShape)
        )
    }

}

@Composable
fun CircularIconButton(
    icon: Int = 0,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .then(modifier)
    )
    {


        Box(
            modifier = Modifier
                .size(40.dp)
                .background(grayShade1, CircleShape)
                .clickable {
                    onClick.invoke()
                }
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Round Image",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
            )
        }
    }

}


@Composable
fun CircularIconButton2(
    icon: Int = 0,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    color: Color,
    text : String = ""
) {
    Box(
        modifier = Modifier
            .then(modifier)
    )
    {
        Row()
        {
            Text(
                text = text,
                color = primaryColor,
                fontSize = 20.sp,
                fontFamily = customFontFamily,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        onClick.invoke()
                    }
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color, CircleShape)
                    .clickable {
                        onClick.invoke()
                    }
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Round Image",
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(CircleShape)
                )
            }

        }
    }

}