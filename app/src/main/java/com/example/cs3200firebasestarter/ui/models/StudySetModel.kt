package com.example.cs3200firebasestarter.ui.models

data class StudySetModel(
    val id : String? = null,
    val userId : String? = null,
    val description: String? = null,
    val studySetName: String? = null,
    val studySet : MutableList<CardData>? = null,
    val accuracy : Int? = null
)