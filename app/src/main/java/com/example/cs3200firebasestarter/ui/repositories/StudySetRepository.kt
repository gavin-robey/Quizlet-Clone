package com.example.cs3200firebasestarter.ui.repositories

import com.example.cs3200firebasestarter.ui.models.CardData
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

    suspend fun updateStudySet(studySet: StudySetModel){
        Firebase.firestore
            .collection("StudySet")
            .document(studySet.id!!)
            .set(studySet)
            .await()

        val oldIndex = studySetCache.indexOfFirst {
            it.id == studySet.id
        }
        studySetCache[oldIndex] = studySet
    }

    suspend fun deleteSet(studySet: StudySetModel){
        try {
            Firebase.firestore
                .collection("StudySet")
                .document(studySet.id!!)
                .delete()
                .await()

            studySetCache.removeAll { it.id == studySet.id }
        } catch (e: Exception) {
            println(e)
        }
    }

    suspend fun getStudySets() : List<StudySetModel>{
        if(!cacheInitialized){
            val snapshot = Firebase.firestore
                .collection("StudySet")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .get()
                .await()
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
        doc.set(studySetModel).await()
        studySetCache.add(studySetModel)
        return studySetModel
    }
}