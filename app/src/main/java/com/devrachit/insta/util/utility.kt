package com.devrachit.insta.util

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.ui.theme.errorColor
import kotlinx.coroutines.launch


fun navigateToScreen(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(route) {
            inclusive = true
        }
        launchSingleTop = true
    }

}

@Composable
fun CommonDivider() {
    Divider(
        color = Color.White,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.9f)
            .padding(top = 12.dp, bottom = 8.dp)
            .width(100.dp)
    )
}

@Composable
fun CommonImage(
    data: String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop
) {
    Log.d("CommonImage", "CommonImage: $data")
    val painter = rememberAsyncImagePainter(model = data)
    Box(
        modifier = modifier.background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )
    }

}

@Composable
fun ButtonImage(painter: Painter) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .width(24.dp)
            .height(24.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CommonRow(imageUrl: String?, name: String?, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clickable { onItemClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonImage(
            data = imageUrl,
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Red)

        )
        Text(
            text = name ?: "",
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun SelfRunningImageCarousel(images: List<Painter>, interval: Long = 5000L) {
    var currentPage by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberLazyListState()
    val handler = remember { Handler(Looper.getMainLooper()) }

    DisposableEffect(Unit) {
        val runnable = object : Runnable {
            override fun run() {
                currentPage = (currentPage + 1) % images.size
                coroutineScope.launch {
                    pagerState.animateScrollToItem(currentPage)
                }
                handler.postDelayed(this, interval)
            }
        }
        handler.postDelayed(runnable, interval)

        onDispose {
            handler.removeCallbacks(runnable)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
            .padding(end = 24.dp, top = 58.dp, start = 0.dp)
    ) {
        LazyRow(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(390.dp),
            horizontalArrangement = Arrangement.spacedBy(100.dp),
            contentPadding = PaddingValues(horizontal = 30.dp)
        ) {
            items(images.size) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .clipToBounds(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = images[index],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f, matchHeightConstraintsFirst = true)
                            .clipToBounds(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}

@Composable
fun errorFeild(message:String="", showMessage: Boolean){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = if(!showMessage)message else "",
            modifier = Modifier
                .padding(top = 8.dp, start = 24.dp),
            fontFamily = customFontFamily,
            color = errorColor,
            fontSize = 14.sp
        )
    }
}
@Composable
fun errorFeild2(message:String="" , showMessage: Boolean){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = if(!showMessage)message else "",
            modifier = Modifier
                .padding(top = 8.dp),
            fontFamily = customFontFamily,
            color = errorColor,
            fontSize = 14.sp
        )
    }
}