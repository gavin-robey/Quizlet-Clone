package com.example.cs3200firebasestarter.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.cs3200firebasestarter.ui.repositories.UserRepository
import com.example.cs3200firebasestarter.ui.screens.BuildCharacterScreen
import com.example.cs3200firebasestarter.ui.screens.HomeScreen
import com.example.cs3200firebasestarter.ui.screens.LaunchScreen
import com.example.cs3200firebasestarter.ui.screens.ResultsScreen
import com.example.cs3200firebasestarter.ui.screens.SignInScreen
import com.example.cs3200firebasestarter.ui.screens.SignUpScreen
import com.example.cs3200firebasestarter.ui.screens.SplashScreen
import com.example.cs3200firebasestarter.ui.screens.TermsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        floatingActionButton = {
            if (currentDestination?.hierarchy?.none {
                    it.route == Routes.launchNavigation.route ||
                            it.route == Routes.splashScreen.route || it.route == "buildcharacter?id={id}"
                            || it.route == "termsScreen?id={id}" || it.route == "resultsScreen?percentage={percentage}"
                } == true) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Routes.buildCharacter.route)
                    },
                    containerColor = Color(66, 62, 216)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
                }
            }
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = Routes.splashScreen.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            navigation(
                route = Routes.launchNavigation.route,
                startDestination = Routes.launch.route
            ) {
                composable(route = Routes.launch.route) { LaunchScreen(navController) }
                composable(route = Routes.signIn.route) { SignInScreen(navController) }
                composable(route = Routes.signUp.route) { SignUpScreen(navController) }
            }
            navigation(
                route = Routes.appNavigation.route,
                startDestination = Routes.home.route
            ) {
                composable(route = Routes.home.route) { HomeScreen(navController) }
            }
            composable(route = Routes.splashScreen.route) { SplashScreen(navController) }

            // route for the results screen
            composable(
                route = "resultsScreen?percentage={percentage}",
                arguments = listOf(navArgument("percentage"){ defaultValue = "new"})
            ) {
                navBackStackEntry ->
                ResultsScreen(navController, navBackStackEntry.arguments?.get("percentage").toString()
                )
            }

            // change this to buildStudySet
            composable(
                route = "buildcharacter?id={id}", // question mark is an optional argument
                arguments = listOf(navArgument("id") { defaultValue = "new" })
            ) { navBackStackEntry ->
                BuildCharacterScreen(
                    navController,
                    navBackStackEntry.arguments?.get("id").toString()
                )
            }

            // Goes to the terms screen
            composable(
                route = "termsScreen?id={id}", // question mark is an optional argument
                arguments = listOf(navArgument("id") { defaultValue = "new" })
            ) { navBackStackEntry ->
                TermsScreen(
                    navController,
                    navBackStackEntry.arguments?.get("id").toString()
                )
            }
        }
    }

}
