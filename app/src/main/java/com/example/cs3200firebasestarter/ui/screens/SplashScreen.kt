package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.repositories.UserRepository
import com.example.cs3200firebasestarter.ui.theme.button
import kotlinx.coroutines.*

@Composable
fun SplashScreen(navHostController: NavHostController) {

    LaunchedEffect(true) {
        val loginStatusCheck = async {
            // TODO: check to see if user is logged in
        }
        // wait for 3 seconds or until the login check is
        // done before navigating
        delay(700)
        loginStatusCheck.await()
        navHostController.navigate(
            if (UserRepository.getCurrentUserId() == null) Routes.launchNavigation.route else Routes.appNavigation.route) {
            // makes it so that we can't get back to the
            // splash screen by pushing the back button
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = button),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 200.sp,
                fontWeight = FontWeight.Bold,
                text = "Q"
            )
        }
    }
}
