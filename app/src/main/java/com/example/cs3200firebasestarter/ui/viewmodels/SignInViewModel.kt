package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.repositories.CharacterRepository
import com.example.cs3200firebasestarter.ui.repositories.StudySetRepository
import com.example.cs3200firebasestarter.ui.repositories.UserRepository

class SignInScreenState {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailError by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
}

class SignInViewModel(application: Application): AndroidViewModel(application) {
    val uiState = SignInScreenState()

    suspend fun signIn() {
        // clear existing errors
        uiState.emailError = false
        uiState.passwordError = false
        uiState.errorMessage = ""
        StudySetRepository.clearCache() // clear the cache so that nothing is on there from the previous user
        if (uiState.email.isEmpty()) {
            uiState.emailError = true
            uiState.errorMessage = "Email cannot be blank"
            return
        }

        if (uiState.password.isEmpty()) {
            uiState.passwordError = true
            uiState.errorMessage = "Password cannot be blank."
            return
        }
        UserRepository.loginUser(uiState.email, uiState.password)
    }
}