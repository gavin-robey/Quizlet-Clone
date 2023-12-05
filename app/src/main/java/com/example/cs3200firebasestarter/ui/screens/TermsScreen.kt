package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.animation.AnimatedDeck
import com.example.cs3200firebasestarter.ui.animation.ProgressBar
import com.example.cs3200firebasestarter.ui.components.AmountsRow
import com.example.cs3200firebasestarter.ui.viewmodels.AddStudySetViewModel

@Composable
fun TermsScreen(navHostController: NavHostController, id : String?){
    val viewModel: AddStudySetViewModel = viewModel()
    val state = viewModel.uiState
    var correctAmount by remember { mutableIntStateOf(0) }
    var incorrectAmount by remember { mutableIntStateOf(0) }
    var currentCard by remember { mutableIntStateOf(1) }
    var deckSize by remember { mutableIntStateOf(1) }


    LaunchedEffect(true){
        viewModel.setUpInitialState(id)
        state.setupComplete = true
    }

    if(state.setupComplete) {
        Column(modifier = Modifier
            .background(color = Color(0, 9, 45))
        ){
            Row(
                modifier = Modifier.padding(10.dp)
            ){
                Button(
                    onClick = {
                        navHostController.popBackStack()
                    },
                    modifier = Modifier
                        .weight(1f)  // Take up remaining space
                        .padding(vertical = 0.dp, horizontal = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0, 9, 45),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Incorrect"
                    )
                }

                Text(
                    modifier = Modifier
                        .weight(1f)  // Take up remaining space
                        .align(Alignment.CenterVertically),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = "$currentCard / $deckSize"
                )

                Button(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)  // Take up remaining space
                        .padding(vertical = 0.dp, horizontal = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0, 9, 45),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = " ")
                }
            }
            
            ProgressBar(currentCount = incorrectAmount + correctAmount, deckSize = deckSize)
            AmountsRow(incorrectAmount = incorrectAmount, correctAmount = correctAmount)

            AnimatedDeck(
                cards = state.studySetList,
                onCurrentCardChange = {newAmount -> currentCard = newAmount},
                onDeckSizeChange = {newAmount -> deckSize = newAmount},
                onCorrectAmountChange = { newCorrectAmount -> correctAmount = newCorrectAmount },
                onIncorrectAmountChange = { newIncorrectAmount -> incorrectAmount = newIncorrectAmount }
            )
        }
    }
}



