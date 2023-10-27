package com.example.cs3200firebasestarter.ui.repositories

import com.example.cs3200firebasestarter.ui.models.CharacterModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await

object CharacterRepository {
    private val characterCache = mutableListOf<CharacterModel>()
    private var cacheInitialized = false

    fun clearCache(){
        characterCache.clear()
        cacheInitialized = false
    }

    suspend fun getCharacters() : List<CharacterModel>{
        if(!cacheInitialized){
            val snapshot = Firebase.firestore
                .collection("Characters")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .get()
                .await()
            characterCache.addAll(snapshot.toObjects())
            cacheInitialized = true
        }
        return characterCache
    }

    suspend fun createCharacter(
        name: String?,
        age : String?,
        race : String?,
        _class : String?,
        height : String?,
        gender : String?,
        description : String?
    ) : CharacterModel {
        val doc = Firebase.firestore.collection("Characters").document()
        val character = CharacterModel(
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            name = name,
            age = age,
            race = race,
            _class = _class,
            height = height,
            gender = gender,
            description = description
        )
        doc.set(character).await()
        characterCache.add(character)
        return character
    }

    suspend fun updateCharacter(character : CharacterModel){
        // force it to be a string
        Firebase.firestore
            .collection("Characters")
            .document(character.id!!)
            .set(character)
            .await()

        // find the index of the old character element
        val oldCharacterIndex = characterCache.indexOfFirst {
            it.id == character.id
        }
        characterCache[oldCharacterIndex] = character   // replace the old element in cache with the new element
    }
}