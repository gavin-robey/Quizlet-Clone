package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.models.StudySetModel
import com.example.cs3200firebasestarter.ui.repositories.StudySetRepository

class StudySetScreenState{
    val _studySets = mutableStateListOf<StudySetModel>()
    val studySets : List<StudySetModel> get() = _studySets
}

class StudySetViewModel(application: Application) : AndroidViewModel(application){
    val uiState = StudySetScreenState()

    suspend fun getStudySets(){
        val studySets = StudySetRepository.getStudySets()
        uiState._studySets.clear() // so that we don't have duplicates
        uiState._studySets.addAll(studySets)
    }
}