package com.example.cs3200firebasestarter.ui.repositories

import com.example.cs3200firebasestarter.ui.models.CardData
import com.example.cs3200firebasestarter.ui.models.StudySetModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object StudySetRepository {
    private val studySetCache = mutableListOf<StudySetModel>()
    private var cacheInitialized = false

    fun clearCache(){
        studySetCache.clear()
        cacheInitialized = false
    }

    suspend fun createStudySet(
        studySetName : String?,
        description : String?,
        studySet: MutableList<CardData>?
    ) : StudySetModel {
        val doc = Firebase.firestore.collection("StudySet").document()
        val studySetModel = StudySetModel(
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            studySetName = studySetName,
            description = description,
            studySet = studySet
        )
        doc.set(studySetModel).await()
        studySetCache.add(studySetModel)
        return studySetModel
    }
}