package com.devrachit.insta.ui.DashBoardScreens.SearchScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.devrachit.insta.Constants.Constants
import com.devrachit.insta.R
import com.devrachit.insta.ui.DashBoardScreens.HomeScreen.CircularIconButton
import com.devrachit.insta.ui.DashBoardScreens.HomeScreen.ImageExample
import com.devrachit.insta.ui.theme.black
import com.devrachit.insta.ui.theme.gray
import com.devrachit.insta.ui.theme.grayShade1
import com.devrachit.insta.ui.theme.grayShade2
import com.devrachit.insta.ui.theme.grayShade3
import com.devrachit.insta.ui.theme.primaryColor
import com.devrachit.insta.util.clickableWithoutRipple

@Composable
fun topBar(
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit,
    imageUrl: String = "",
    userName: String = "",
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    isSearching: Boolean,
    persons: List<Person>

) {
    @OptIn(ExperimentalFoundationApi::class)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(grayShade2),
        verticalAlignment = Alignment.CenterVertically

    )
    {
        ImageExample(
            imageUrl,
            modifier = Modifier
                .padding(start = 16.dp)
                .clickableWithoutRipple(onProfileClick)
        )
        @OptIn(ExperimentalFoundationApi::class)
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            modifier = Modifier
                .width(200.dp)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            placeholder = { Text(text = "Search") },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = CircleShape,
            singleLine = true,
            colors =
            TextFieldDefaults.colors(
                focusedContainerColor = grayShade1,
                unfocusedContainerColor = grayShade1,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = primaryColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,

                )
        )
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

