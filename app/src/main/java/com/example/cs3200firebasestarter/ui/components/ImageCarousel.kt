package com.example.cs3200firebasestarter.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(imageList : List<CarouselItem>) {
    val state = rememberLazyListState()

    Column {
        // Image Carousel
        LazyRow(
            state = state,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
        ) {
            items(imageList) { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = item.imageResource),
                        contentDescription = null,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(25.dp, 0.dp, 25.dp, 0.dp)
                            .height(260.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        modifier = Modifier
                            .width(400.dp)
                            .padding(25.dp),
                        text = item.text,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        softWrap = true,
                        style = TextStyle(
                            lineHeight = 40.sp
                        )
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(60.dp))
        DotsIndicator(
            dotCount = imageList.size,
            currentIndex = state.firstVisibleItemIndex,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
