package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.animation.CircularLoading
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.theme.background
import com.example.cs3200firebasestarter.ui.viewmodels.AddStudySetViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun ResultsScreen(navHostController: NavHostController, percentage : String){
    val totalPercent = (percentage.toFloat() * 100F)
    val angle by remember { mutableFloatStateOf(percentage.toFloat() * 360) }
    val animatableAngle = remember { Animatable(angle) }

    LaunchedEffect(true) {
        animatableAngle.animateTo(percentage.toFloat() * 360, animationSpec = tween(durationMillis = 3000))
    }

    Column(
        modifier =  Modifier
            .fillMaxSize()
            .background(color = background)
            .padding(15.dp)
    ){
        Row{
            Button(
                onClick = {
                    navHostController.navigate(Routes.home.route)
                },
                modifier = Modifier
                    .padding(vertical = 0.dp, horizontal = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = background,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Incorrect"
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().weight(1F)
        ){
            CircularLoading(
                startAngle = 0F,
                radius = 300.dp,
                sweepAngle = animatableAngle.value,
                text = "${totalPercent.toInt()} %"
            )
        }
        // put ad here
        // ca-app-pub-3940256099942544/6300978111
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    loadAd(AdRequest.Builder().build())
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

}