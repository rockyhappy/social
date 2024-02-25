package com.devrachit.insta.ui.DashBoardScreens.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.TextButtonDefaults
import coil.compose.rememberAsyncImagePainter
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.ui.theme.grayShade1


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
                .width(112.dp)
                .height(112.dp)
                .clip(CircleShape)
//                .border(0.dp, primaryColor, CircleShape)
        )
    }

}

@Composable
fun grayButton(onClick: () -> Unit, text: String) {
    Box(
        modifier=Modifier
            .wrapContentWidth()
    )
    {
        Button(
            onClick = { onClick.invoke() },
            colors = ButtonDefaults.textButtonColors(
                containerColor = grayShade1,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .height(50.dp)
                .width(156.dp),

            )
        {
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = customFontFamily,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
    }
}