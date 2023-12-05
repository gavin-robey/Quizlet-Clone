package com.example.cs3200firebasestarter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cs3200firebasestarter.ui.models.StudySetModel

@Composable
fun StudySetCard(
    studySet: StudySetModel,
    userName : String,
    onClick: () -> Unit
){
    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Max)
                .clickable {
                    onClick()
                }
                .padding(10.dp),
            shadowElevation = 8.dp,
            color = Color(46, 56, 86),
            shape = RoundedCornerShape(8.dp)
        ){
            Column(
                modifier = Modifier.padding(10.dp)
            ){
                studySet.studySetName?.let {
                    Text(
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(246, 247, 251),
                        text = it
                    )
                }
                val size = studySet.studySet?.size?.toInt()
                Text(
                    color = Color(246, 247, 251),
                    text = "$size term${if (size != null && size > 1) "s" else ""}"
                )
                
                Spacer(modifier = Modifier.height(50.dp))

                Row(
                ){
                    CircleAvatar(userName = userName)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

fun colorFromName(userName: String) : Color {
    val orange = Color(255, 87, 34)
    val green = Color(76, 175, 80)
    val blue = Color(33, 150, 243)
    val yellow = Color(255, 193, 7)
    val pink = Color(233, 30, 99)
    val purple = Color(171, 71, 188)
    val firstLetterAscii = userName.take(1).uppercase().toCharArray()[0].toInt()

    return when (firstLetterAscii % 6) {
        0 -> orange
        1 -> green
        2 -> blue
        3 -> yellow
        4 -> pink
        else -> purple
    }
}


@Composable
fun CircleAvatar(userName: String) {
    Box(
        modifier = Modifier
            .height(30.dp)
            .width(30.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            color = colorFromName(userName),
        ) {
            Text(
                text = userName.take(1).uppercase(),
                textAlign = TextAlign.Center,
                color = Color(246, 247, 251),
                fontWeight = FontWeight.Bold,
            )
        }
    }

    Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing between the circle and the text

    Text(
        color = Color(246, 247, 251),
        text = userName
    )
}