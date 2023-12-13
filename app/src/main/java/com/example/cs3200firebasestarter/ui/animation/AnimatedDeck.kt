package com.example.cs3200firebasestarter.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.cs3200firebasestarter.ui.models.CardData
import com.example.cs3200firebasestarter.ui.theme.background
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun AnimatedDeck(
    cards: MutableList<CardData>,
    onCurrentCardChange : (Int) -> Unit,
    onDeckSizeChange : (Int) -> Unit,
    onCorrectAmountChange: (Int) -> Unit,
    onIncorrectAmountChange: (Int) -> Unit
) {
    val animatedOffsetX = remember { Animatable(0f) }
    var index by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    val cardList = remember { cards }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(index) {
        onCurrentCardChange(index + 1)
        onDeckSizeChange(cardList.size)
        isFlipped = cardList[index].isFlipped ?: false
        isCorrect = cardList[index].isCorrect
    }

    LaunchedEffect(true){
        index = 0 // resets all settings on initial load
        isFlipped = false
        for(card in cardList){
            card.isFlipped = null // original values are null
            card.isCorrect = null
        }
    }

    LaunchedEffect(animatedOffsetX.value) {
        val breakpoint = 50f

        if (animatedOffsetX.value <= -breakpoint && index < cardList.size - 1 && isCorrect != null) {
            animatedOffsetX.animateTo(-1000f / 3, animationSpec = tween(durationMillis = 40))
            index += 1
            animatedOffsetX.snapTo(0f)
        } else if (animatedOffsetX.value >= breakpoint && index > 0) {
            animatedOffsetX.animateTo(1000f / 3, animationSpec = tween(durationMillis = 40))
            index -= 1
            animatedOffsetX.snapTo(0f)
        } else {
            animatedOffsetX.animateTo(0f, animationSpec = tween(durationMillis = 70))
        }
    }

    LaunchedEffect(isCorrect) {
        var tempCorrect = 0
        var tempIncorrect = 0
        for (card in cardList) {
            if (card.isCorrect == true) {
                tempCorrect++
            } else if (card.isCorrect == false) {
                tempIncorrect++
            }
        }
        onCorrectAmountChange(tempCorrect)
        onIncorrectAmountChange(tempIncorrect)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = background)
                    .offset {
                        IntOffset(animatedOffsetX.value.roundToInt(), 0)
                    }
                    .graphicsLayer {
                        translationX = animatedOffsetX.value
                    }
                    .pointerInput(Unit) {
                        coroutineScope {
                            while (true) {
                                val pointerId = awaitPointerEventScope {
                                    awaitFirstDown().id
                                }
                                animatedOffsetX.stop()
                                awaitPointerEventScope {
                                    horizontalDrag(pointerId) {
                                        launch {
                                            if (!isFlipped && it.positionChange().x >= 0) {
                                                animatedOffsetX.snapTo(animatedOffsetX.value + (it.positionChange().x / 3f))
                                            } else if (isFlipped && it.positionChange().x <= 0) {
                                                animatedOffsetX.snapTo(animatedOffsetX.value + (it.positionChange().x / 3f))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
            ) {
                Box {
                    val previousElement = Helper().getPreviousElement(cardList, index)
                    val currentElement = cardList[index]
                    val nextElement = Helper().getNextElement(cardList, index)

                    if (previousElement != null) {
                        CardComponent(
                            frontText = previousElement.front ?: "",
                            backText = previousElement.back ?: "",
                            offsetX = animatedOffsetX.value - 1000f,
                            isFlipped = previousElement.isFlipped ?: false,
                            onFlippedChange = {}
                        )
                    }

                    CardComponent(
                        frontText = currentElement.front ?: "",
                        backText = currentElement.back ?: "",
                        offsetX = animatedOffsetX.value,
                        isFlipped = currentElement.isFlipped ?: false,
                        onFlippedChange = { newValue ->
                            cardList[index].isFlipped = newValue
                            isFlipped = newValue
                        }
                    )

                    if (nextElement != null) {
                        CardComponent(
                            frontText = nextElement.front ?: "",
                            backText = nextElement.back ?: "",
                            offsetX = animatedOffsetX.value + 1000f,
                            isFlipped = nextElement.isFlipped ?: false,
                            onFlippedChange = {}
                        )
                    }
                }
            }
        }

        // Buttons for "Correct" and "Incorrect"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = background)
                .padding(30.dp)

        ) {
            Button(
                onClick = {
                    isCorrect = false
                    cardList[index].isCorrect = isCorrect
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f)
                    .padding(vertical = 0.dp, horizontal = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                enabled = isFlipped,
            ) {
                if(isFlipped){
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Incorrect", tint = Color.White)
                }else{
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Incorrect", tint = Color(40, 46, 62))
                }
            }
            
            Spacer(modifier = Modifier.width(150.dp))

            Button(
                onClick = {
                    isCorrect = true
                    cardList[index].isCorrect = isCorrect
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f)
                    .padding(vertical = 0.dp, horizontal = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                enabled = isFlipped,
            ) {
                if(isFlipped){
                    Icon(imageVector = Icons.Default.Check, contentDescription = "correct", tint = Color.White)
                }else{
                    Icon(imageVector = Icons.Default.Check, contentDescription = "correct", tint = Color(40, 46, 62))
                }
            }
        }
    }
}



