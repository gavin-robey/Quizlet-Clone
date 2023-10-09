package com.example.cs3200firebasestarter.ui.navigation

data class Screen(val route: String)

object Routes {
    val launchNavigation = Screen("launchnavigation")
    val appNavigation = Screen("appnavigation")
    val launch = Screen("launch")
    val signIn = Screen("signin")
    val signUp = Screen("signup")
    val splashScreen = Screen("splashscreen")
    val home = Screen(route = "home")
}