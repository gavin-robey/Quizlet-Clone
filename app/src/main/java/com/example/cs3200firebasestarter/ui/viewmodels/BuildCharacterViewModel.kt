package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.GenerateRandomCharacter
import com.example.cs3200firebasestarter.ui.repositories.CharacterRepository
import kotlin.random.Random

class BuildCharacterScreenState {
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var race by mutableStateOf("")
    var _class by mutableStateOf("")
    var height by mutableStateOf("")
    var gender by mutableStateOf("")
    var description by mutableStateOf("")
    var emptyFields by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var saveSuccess by mutableStateOf(false)
}
class BuildCharacterViewModel(application: Application) : AndroidViewModel(application){
    val uiState = BuildCharacterScreenState()
    var id : String? = null

    suspend fun buildCharacter(){
        uiState.emptyFields = false
        uiState.errorMessage = ""
        if( uiState.name.isEmpty() || uiState.age.isEmpty() || uiState.race.isEmpty()||
            uiState._class.isEmpty() || uiState.height.isEmpty() || uiState.gender.isEmpty()
            || uiState.description.isEmpty()
        ){
            uiState.emptyFields = true
            uiState.errorMessage = "No fields can be left empty"
            return
        }
        if(id == null){
            CharacterRepository.createCharacter(
                uiState.name,
                uiState.age,
                uiState.race,
                uiState._class,
                uiState.height,
                uiState.gender,
                uiState.description
            )
        }else{
            val character = CharacterRepository.getCharacters().find { it.id == id } ?: return
            CharacterRepository.updateCharacter(
                character.copy(
                    name = uiState.name,
                    age = uiState.age,
                    race = uiState.race,
                    _class = uiState._class,
                    height = uiState.height,
                    gender = uiState.gender,
                    description = uiState.description
                )
            )
        }
        uiState.saveSuccess = true
    }

    suspend fun setUpInitialState(id : String?){
        if(id == null || id == "new") return
        this.id = id
        val character = CharacterRepository.getCharacters().find { it.id == id } ?: return
        uiState.name = character.name ?: ""
        uiState.age = character.age ?: ""
        uiState.race = character.race ?: ""
        uiState._class = character._class ?: ""
        uiState.height = character.height ?: ""
        uiState.gender = character.gender ?: ""
        uiState.description = character.description ?: ""
    }

    fun createRandomCharacter() {
        val randomChar = GenerateRandomCharacter()
        uiState.name = randomChar.getRandomElement(randomChar.names)
        uiState.age = (Random.nextInt(1, 100) + 1).toString()
        uiState.race = randomChar.getRandomElement(randomChar.races)
        uiState._class = randomChar.getRandomElement(randomChar.classes)
        uiState.height = "${Random.nextInt(140, 210)} cm"
        uiState.gender = randomChar.getRandomElement(randomChar.genders)
        uiState.description = randomChar.getRandomElement(randomChar.descriptions)
    }
}