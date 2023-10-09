package com.example.cs3200firebasestarter.ui.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object UserRepository {
    suspend fun createUser(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun loginUser(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}