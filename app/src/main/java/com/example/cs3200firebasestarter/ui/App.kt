package com.example.cs3200firebasestarter.ui

import androidx.compose.runtime.Composable
import com.example.cs3200firebasestarter.ui.navigation.RootNavigation
import com.example.cs3200firebasestarter.ui.theme.CS3200FirebaseStarterTheme

@Composable
fun App() {
    CS3200FirebaseStarterTheme {
        RootNavigation()
    }
}