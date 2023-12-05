package com.example.cs3200firebasestarter.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(currentCount: Int, deckSize: Int) {
    var statusBarText by remember { mutableStateOf("Status: $currentCount / $deckSize") }

    DisposableEffect(currentCount, deckSize) {
        statusBarText = "Status: $currentCount / $deckSize"
        onDispose { }
    }

    // Animatable for moving the bar to the right
    val animatable = remember { Animatable(0f) }

    // Animate to the position based on the currentCard
    LaunchedEffect(currentCount) {
        animatable.animateTo(currentCount.toFloat(), animationSpec = tween(durationMillis = 500))
    }

    // background bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)
    ) {
        // Background bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(color = Color(40, 46, 62))
        )

        // Moving bar
        Box(
            modifier = Modifier
                .fillMaxWidth(animatable.value / deckSize)
                .height(3.dp)
                .background(color = Color.White)
        )
    }
}