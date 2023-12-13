package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.R
import com.example.cs3200firebasestarter.ui.components.CarouselItem
import com.example.cs3200firebasestarter.ui.components.ImageCarousel
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.theme.background
import com.example.cs3200firebasestarter.ui.theme.button
import com.example.cs3200firebasestarter.ui.theme.secondaryButton

@Composable
fun LaunchScreen(navHostController: NavHostController) {
    val imageList = listOf(
        CarouselItem(R.drawable.study1, "100% of students who use Quizbit are named Gavin."),
        CarouselItem(R.drawable.study2, "Study flashcards in a fluid and intuitive way."),
        CarouselItem(R.drawable.study3, "Customize flashcards to your exact needs."),
        CarouselItem(R.drawable.study4, "Quizbit is much better than filthy Quizlet."),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            text = "Quizbit",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(60.dp))
        ImageCarousel(imageList)
        Column(
            modifier = Modifier.padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    navHostController.navigate(Routes.signUp.route)
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = button
                ),
            ) {
                Text(
                    fontSize = 18.sp,
                    text = "Sign Up For Free"
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { navHostController.navigate(Routes.signIn.route) }) {
                    Text(
                        color = secondaryButton,
                        fontSize = 18.sp,
                        text = "Or Log In"
                    )
                }
            }
        }
    }
}
