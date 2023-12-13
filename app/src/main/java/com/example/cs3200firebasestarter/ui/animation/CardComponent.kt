package com.example.cs3200firebasestarter.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cs3200firebasestarter.ui.theme.cardColor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CardComponent(
    frontText : String,
    backText : String,
    offsetX: Float,
    isFlipped : Boolean,
    onFlippedChange: (Boolean) -> Unit // Allows Card composable to change external flip state
){
    val rotation = remember { Animatable(0f) }
    var flipped by remember { mutableStateOf(false) }
    // This ensures the internal flip state matches the externally saved state of the current card
    flipped = isFlipped

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(600.dp)
                .graphicsLayer {
                    rotationY = rotation.value
                }
                .offset {
                    IntOffset(offsetX.roundToInt(), 0)
                }
                .pointerInput(Unit) {
                    coroutineScope {
                        while (true) {
                            val pointerId = awaitPointerEventScope {
                                awaitFirstDown().id
                            }
                            rotation.stop()
                            awaitPointerEventScope {
                                horizontalDrag(pointerId) {
                                    launch {
                                        // if the card is flipped, only the right side of the card is
                                        // swipe-able. Otherwise, only the left side of the card is
                                        // swipe-able
                                        if (!flipped && it.positionChange().x <= 0) {
                                            val (newRotation, newIsFlipped) = Helper().clamp(
                                                rotation.value + (it.positionChange().x / 5f),
                                                isFlipped = flipped
                                            )
                                            rotation.snapTo(newRotation)
                                            flipped = newIsFlipped
                                        } else if(flipped && it.positionChange().x >= 0){
                                            val (newRotation, newIsFlipped) = Helper().clamp(
                                                rotation.value + (it.positionChange().x / 5f),
                                                isFlipped = flipped
                                            )
                                            rotation.snapTo(newRotation)
                                            flipped = newIsFlipped
                                        }
                                    }
                                }
                            }
                            // Once the touch event is over, the card rotates to zero
                            launch {
                                rotation.animateTo(0f)
                            }
                            // Once the flipping animation is complete, the external flip state is updated
                            onFlippedChange(flipped)
                        }
                    }
                },
            color = cardColor,
            shadowElevation = 10.dp,
            shape = RoundedCornerShape(10.dp)
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (flipped) {
                    Text(
                        text = backText,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = frontText,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}