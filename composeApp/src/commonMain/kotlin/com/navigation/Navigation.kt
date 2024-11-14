package com.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cardfy.ui.HomeScreen
import com.cardfy.ui.SuccessScannedScreen
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.HOME) {
        composable(Route.HOME) {
            HomeScreen(
                navController = navController
            )
        }



        composable(
            route = "${Route.SUCCESS_SCANNED_SCREEN}/{${Route.SUCCESS_SCANNED_INFO}}",
            arguments = listOf(navArgument(Route.SUCCESS_SCANNED_INFO) {
                type = NavType.StringType
            }
            )
        ) {
            SuccessScannedScreen(
                navController = navController,
                scannedInfo = it.arguments?.getString(Route.SUCCESS_SCANNED_INFO) ?: ""
            )
        }
    }
}