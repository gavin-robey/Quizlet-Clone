package com.example.cs3200firebasestarter.ui.repositories

import com.example.cs3200firebasestarter.ui.models.CardData
import com.example.cs3200firebasestarter.ui.models.CharacterModel
import com.example.cs3200firebasestarter.ui.models.StudySetModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await

object StudySetRepository {
    private val studySetCache = mutableListOf<StudySetModel>()
    private var cacheInitialized = false

    fun clearCache(){
        studySetCache.clear()
        cacheInitialized = false
    }

    suspend fun getStudySets() : List<StudySetModel>{
        if(!cacheInitialized){
            val snapshot = Firebase.firestore
                .collection("StudySet")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .get()
                .await()
            studySetCache.clear()
            studySetCache.addAll(snapshot.toObjects())
            cacheInitialized = true
        }
        return studySetCache
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
        cacheInitialized = false
        doc.set(studySetModel).await()
        studySetCache.add(studySetModel)
        return studySetModel
    }
}