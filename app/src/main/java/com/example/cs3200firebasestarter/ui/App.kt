package com.example.cs3200firebasestarter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cs3200firebasestarter.ui.navigation.RootNavigation
import com.example.cs3200firebasestarter.ui.theme.CS3200FirebaseStarterTheme

@Composable
fun App() {
    CS3200FirebaseStarterTheme(){
        RootNavigation()
    }
}