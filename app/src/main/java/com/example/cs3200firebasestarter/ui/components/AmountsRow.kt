package com.example.cs3200firebasestarter.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AmountsRow(incorrectAmount: Int, correctAmount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // Left side (Incorrect amount)
        Text(
            text = incorrectAmount.toString(),
            color = Color(255, 165, 0), // Orange color
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        )

        // Right side (Correct amount)
        Text(
            text = correctAmount.toString(),
            textAlign = TextAlign.End,
            color = Color.Green,
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        )
    }
}