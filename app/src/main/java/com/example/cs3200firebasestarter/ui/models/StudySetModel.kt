package com.example.cs3200firebasestarter.ui.models

import com.example.cs3200firebasestarter.ui.repositories.UserRepository

data class StudySetModel(
    val id : String? = null,
    val userId : String? = null,
    val studySetName: String? = null,
    val studySet : MutableList<CardData>?
)