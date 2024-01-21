package com.devrachit.insta.ui.ChoiceScreen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class ChoiceScreenComponents {
}
//@Composable
//fun SelfRunningImageCarousel2(images: List<Painter>, interval: Long = 5000L) {
//    var currentPage by remember { mutableStateOf(0) }
//    val coroutineScope = rememberCoroutineScope()
//    val pagerState = rememberPagerState(pageCount = images.size)
//    val handler = remember { Handler(Looper.getMainLooper()) }
//
//    DisposableEffect(Unit) {
//        val runnable = object : Runnable {
//            override fun run() {
//                currentPage = (currentPage + 1) % images.size
//                coroutineScope.launch {
//                    pagerState.scrollToPage(currentPage)
//                }
//                handler.postDelayed(this, interval)
//            }
//        }
//        handler.postDelayed(runnable, interval)
//
//        onDispose {
//            handler.removeCallbacks(runnable)
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(390.dp)
//            .padding(end = 24.dp, top = 58.dp, start = 0.dp)
//    ) {
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(390.dp),
//            verticalAlignment = Alignment.CenterVertically,
//
//        ) { page ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black)
//                    .clipToBounds(),
//                contentAlignment = Alignment.Center
//            ) {
//                Image(
//                    painter = images[page],
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .aspectRatio(1f, matchHeightConstraintsFirst = true)
//                        .clipToBounds(),
//                    contentScale = ContentScale.FillBounds
//                )
//            }
//        }
//    }
//}
