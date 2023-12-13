package com.example.cs3200firebasestarter.ui.repositories

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object UserRepository {
    suspend fun createUser(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun loginUser(email: String, password: String): String? {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthInvalidUserException) {
            return "Please enter a valid email"
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return "Please enter a valid email"
        } catch (e: Exception) {
            return "Your password is incorrect"
        }
        return null
    }

    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}