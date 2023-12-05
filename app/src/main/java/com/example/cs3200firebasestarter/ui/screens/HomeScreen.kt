package com.example.cs3200firebasestarter.ui.screens

import android.graphics.Paint
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.CharacterCard
import com.example.cs3200firebasestarter.ui.components.StudySetCard
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.viewmodels.CharacterViewModel
import com.example.cs3200firebasestarter.ui.viewmodels.StudySetViewModel
import kotlinx.coroutines.delay
import java.lang.Math.cos
import java.lang.Math.sin

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel : StudySetViewModel = viewModel()
    val state = viewModel.uiState
    var isLoaded by remember { mutableStateOf(false) }

    // loads our data base whenever it is updated
    LaunchedEffect(true){
        viewModel.getStudySets()
        isLoaded = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0, 9, 45))
    ){
        Canvas(modifier = Modifier.fillMaxWidth()) {
            translate(top = 100f){
                scale(scaleX = 15f, scaleY = 5f) {
                    drawCircle(Color(52, 62, 86), radius = 20.dp.toPx())
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(52, 62, 86), // Dark grey color for the curved bar
                )
                .graphicsLayer(
                    shape = RoundedCornerShape(
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp,
                        topStart = 0.dp,
                        topEnd = 0.dp
                    )
                )
        ) {
            // Quizbit title
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                text = "Quizbit",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Logout button
            Button(
                onClick = {
                    // Handle logout here
                          // need access to the repository
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(255, 205, 31)
                )
            ) {
                Text(text = "Logout", color = Color(0, 9, 45))
            }
        }

        Spacer(modifier = Modifier.height(75.dp))

        if(isLoaded){
            LazyColumn(){
                items(state.studySets, key = { it.id!! }){
                    StudySetCard(studySet = it, userName = "gavin.robey"){
                        navHostController.navigate("${Routes.termsScreen.route}?id=${it.id!!}")
                    }
                }
            }
        }else{
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                val infiniteTransition = rememberInfiniteTransition(label = "")
                val angle by infiniteTransition.animateFloat(
                    initialValue = 0F,
                    targetValue = 360F,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1500, easing = EaseInBounce),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                CircularLoading(startAngle = angle, sweepAngle = angle + 90f)
            }
        }
    }
}

@Composable
fun CircularLoading(
    fontSize : TextUnit = 75.sp,
    radius : Dp = 125.dp,
    color : Color = Color(66, 62, 216),
    strokeWidth : Dp = 10.dp,
    startAngle : Float,
    sweepAngle : Float
    ){

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius)
    ){
        Canvas(modifier = Modifier.size(radius)){
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "Q",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}


