package com.example.cs3200firebasestarter.ui.animation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cs3200firebasestarter.ui.theme.button

@Composable
fun CircularLoading(
    fontSize : TextUnit = 75.sp,
    radius : Dp = 125.dp,
    color : Color = button,
    strokeWidth : Dp = 10.dp,
    startAngle : Float,
    sweepAngle : Float,
    text : String?
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius)
    ) {
        Canvas(modifier = Modifier.size(radius)) {
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = text ?: "Error",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}