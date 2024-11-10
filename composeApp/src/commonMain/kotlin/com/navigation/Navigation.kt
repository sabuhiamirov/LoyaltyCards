package com.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cardfy.ui.HomeScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.HOME) {
        composable(Route.HOME) {
            HomeScreen(
                navController = navController
            )
        }
    }
}