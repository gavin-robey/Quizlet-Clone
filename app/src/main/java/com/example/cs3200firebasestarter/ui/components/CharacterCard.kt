package com.example.cs3200firebasestarter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Surface
import com.example.cs3200firebasestarter.ui.models.CharacterModel


@Composable
fun CharacterCard(
    character: CharacterModel,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .padding(8.dp),

        shadowElevation = 8.dp,
        color = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
        ) {
            // Title
            Text(
                text = character.name ?: "",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Details
            Text(
                text = "Age: ${character.age ?: "Unknown"}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "Race: ${character.race ?: "Unknown"}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "Class: ${character._class ?: "Unknown"}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "Height: ${character.height ?: "Unknown"}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "Gender: ${character.gender ?: "Unknown"}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "Description:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = character.description ?: "No description available",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}