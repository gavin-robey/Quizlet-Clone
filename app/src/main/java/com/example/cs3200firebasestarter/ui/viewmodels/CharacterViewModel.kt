package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.models.CharacterModel
import com.example.cs3200firebasestarter.ui.repositories.CharacterRepository

class CharacterScreenState{
    val _characters = mutableStateListOf<CharacterModel>()
    val characters : List<CharacterModel> get() = _characters
}


class CharacterViewModel(application: Application) : AndroidViewModel(application){
    val uiState = CharacterScreenState()
    suspend fun getCharacters(){
        val characters = CharacterRepository.getCharacters()
        uiState._characters.clear() // so that we don't have duplicates
        uiState._characters.addAll(characters)
    }
}