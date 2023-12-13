package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.models.CardData
import com.example.cs3200firebasestarter.ui.repositories.CharacterRepository
import com.example.cs3200firebasestarter.ui.repositories.StudySetRepository

class AddStudySetState {
    var term by mutableStateOf("")
    var definition by mutableStateOf("")
    var studySetName by mutableStateOf("")
    var description by mutableStateOf("")
    var studySet = mutableStateListOf<CardData>()
    var notFinished by mutableStateOf(true)
    var saveSuccess by mutableStateOf(false)
    var studySetList = mutableListOf<CardData>()
    var setupComplete by mutableStateOf(false)
    var accuracy by mutableIntStateOf(0)
    var isInEditMode by mutableStateOf(false)
    var updateStudySet by mutableStateOf(false) // used to determine when the study set can be updated
}

class AddStudySetViewModel(application: Application): AndroidViewModel(application){
    val uiState = AddStudySetState()
    var id : String? = null

    suspend fun addStudySet(){
        StudySetRepository.createStudySet(
            studySet = uiState.studySet,
            studySetName = uiState.studySetName,
            description = uiState.description ?: ""
        )
        uiState.saveSuccess = true
    }

    suspend fun setUpInitialState(id : String?){
        if(id == "new" || id == null){
            uiState.isInEditMode = false // ensures that the UI responds to whether the user is editing or not
            return
        }
        uiState.isInEditMode = true
        this.id = id
        val set = StudySetRepository.getStudySets().find { it.id == id } ?: return
        uiState.studySetName = set.studySetName ?: ""
        uiState.description = set.description ?: ""
        uiState.studySetList = set.studySet ?: mutableListOf()

        // converts a mutable list into a mutable state list safely
        if(uiState.studySetList.size > 0){
            for(index in uiState.studySetList.indices){
                uiState.studySet.add(uiState.studySetList[index])
            }
        }
    }

    suspend fun deleteStudySet(){
        val studySet = StudySetRepository.getStudySets().find { it.id == id } ?: return
        StudySetRepository.deleteSet(studySet)
        uiState.saveSuccess = true
    }

    suspend fun updateStudySet(isInEditMode : Boolean){
        val studySet = StudySetRepository.getStudySets().find { it.id == id } ?: return

        StudySetRepository.updateStudySet(
            studySet.copy(
                studySet = uiState.studySet,
                studySetName = uiState.studySetName,
                description = uiState.description,
                accuracy = if(isInEditMode) studySet.accuracy else uiState.accuracy
            )
        )
        uiState.saveSuccess = true
    }

}