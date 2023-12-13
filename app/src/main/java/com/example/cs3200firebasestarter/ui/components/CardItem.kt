package com.example.cs3200firebasestarter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cs3200firebasestarter.ui.models.CardData
import com.example.cs3200firebasestarter.ui.theme.cardColor
import com.example.cs3200firebasestarter.ui.theme.deleteRed

@Composable
fun CardItem(card: CardData, size : Int, onCardChange: (CardData) -> Unit, delete : () -> Unit) {
    Surface(
        color = cardColor,
        shape = RoundedCornerShape(15.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ){
            FormField(
                value = card.front ?: "",
                onValueChange = {
                    onCardChange(CardData(it, card.back, false))
                },
                title = "Term",
                placeholder = {  },
            )
            FormField(
                value = card.back ?: "",
                onValueChange = {
                    // Update the back property of the Card
                    onCardChange(CardData(card.front, it, false))
                },
                title = "Definition",
                placeholder = {  },
            )
            Spacer(modifier = Modifier.height(15.dp))
            if(size > 1){
                Delete(size = 30){
                    delete()
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun Delete(size : Int, delete : () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 0.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(color = deleteRed, shape = CircleShape)
                .clickable {
                    delete()
                }
        ) {
            Icon(
                modifier = Modifier
                    .size((size / 2).dp)
                    .align(Alignment.Center),
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
