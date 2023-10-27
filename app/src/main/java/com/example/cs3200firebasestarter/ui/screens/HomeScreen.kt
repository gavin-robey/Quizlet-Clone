package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.CharacterCard
import com.example.cs3200firebasestarter.ui.viewmodels.CharacterViewModel

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel : CharacterViewModel = viewModel()
    val state = viewModel.uiState

    // loads our data base whenever it is updated
    LaunchedEffect(true){
        viewModel.getCharacters()
    }

    LazyColumn{
        items(state.characters, key = { it.id!! }){
            CharacterCard(character = it){
                navHostController.navigate("buildCharacter?id=${it.id!!}")
            }
        }
    }
}