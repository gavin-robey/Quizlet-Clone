package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.models.CardData
import com.example.cs3200firebasestarter.ui.repositories.StudySetRepository

class AddStudySetState {
    var term by mutableStateOf("")
    var definition by mutableStateOf("")
    var studySetName by mutableStateOf("")
    var description by mutableStateOf("")
    var studySet = mutableStateListOf<CardData>()
    var notFinished by mutableStateOf(true)
    var saveSuccess by mutableStateOf(false)
    var showDescription by mutableStateOf(false)
    var studySetList = mutableListOf<CardData>()
    var setupComplete by mutableStateOf(false)
}

class AddStudySetViewModel(application: Application): AndroidViewModel(application){
    val uiState = AddStudySetState()

    suspend fun addStudySet(){
        StudySetRepository.createStudySet(
            studySet = uiState.studySet,
            studySetName = uiState.studySetName,
            description = uiState.description ?: ""
        )
    }

    suspend fun setUpInitialState(id : String?){
        if(id == "new" || id == null) return
        val set = StudySetRepository.getStudySets().find { it.id == id } ?: return
        uiState.studySetName = set.studySetName ?: ""
        uiState.description = set.description ?: ""
        uiState.studySetList = set.studySet ?: mutableListOf()
    }

    suspend fun deleteStudySet(){

    }

    suspend fun editStudySet(){

    }

}