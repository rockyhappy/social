package com.devrachit.insta.ui.DashBoardScreens.profileScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Insets.add
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.TextButtonDefaults
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.VideoFrameDecoder
import coil.request.videoFrameMillis
import com.devrachit.insta.Constants.Constants.Companion.customFontFamily
import com.devrachit.insta.Models.Tapes
import com.devrachit.insta.R
import com.devrachit.insta.ui.theme.grayShade1
import com.devrachit.insta.ui.theme.grayShade4
import dagger.hilt.android.qualifiers.ApplicationContext


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



@Composable
fun ProfileNavigation(
    selected: Int,
    onItemSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        ProfileNavigationItem(
            text = "Photos",
            selected = selected == 1,
            onClick = { onItemSelected(1) }
        )

        ProfileNavigationItem(
            text = "Tapes",
            selected = selected == 2,
            onClick = { onItemSelected(2) }
        )

        ProfileNavigationItem(
            text = "Stories",
            selected = selected == 3,
            onClick = { onItemSelected(3) }
        )

        ProfileNavigationItem(
            text = "Tagged",
            selected = selected == 4,
            onClick = { onItemSelected(4) }
        )
    }
}

@Composable
private fun ProfileNavigationItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (selected) Color.White else grayShade4,
            modifier = Modifier.clickable { onClick() }
        )
        if (selected) {
            Image(
                painter = painterResource(id = R.drawable.dot_primary_color),
                contentDescription = null,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
 fun StaggeredPostList(items: List<String>)
{
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {

        items(items.size) { item ->
            AsyncImage(
                model = items[item],
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(
                        shape = RoundedCornerShape(
                            corner = CornerSize(16.dp)
                        )
                    )
            )
        }

    }
}
@Composable
fun StaggeredTapesList(items: List<Tapes>, context: Context)
{
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        items(items.size) { item ->
            VideoThumbnailFromUrl(context, items[item].url)
        }

    }
}

@Composable
fun VideoThumbnailFromUrl(context: Context, videoUrl: String) {
    val bitmap: Bitmap? = remember {
        createVideoThumb(context, Uri.parse(videoUrl))
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null // Provide content description if needed
        )
    } ?: run {
        // Handle error case
        Toast.makeText(context, "Error loading video thumbnail", Toast.LENGTH_SHORT).show()
    }
}