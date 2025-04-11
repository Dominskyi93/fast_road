package com.chicken.fast.road.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chicken.fast.road.ui.screens.custom.CustomScreen
import com.chicken.fast.road.ui.screens.game.GameScreen
import com.chicken.fast.road.ui.screens.home.HomeScreen
import com.chicken.fast.road.ui.screens.records.RecordsScreen
import com.chicken.fast.road.ui.screens.settings.SettingsScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Route.HOME.route) {

        composable(Route.HOME.route) {
            HomeScreen(navController)
        }
        composable(Route.GAME.route) {
            GameScreen(navController)
        }
        composable(Route.CUSTOM.route) {
            CustomScreen()
        }
        composable(Route.RECORDS.route) {
            RecordsScreen()
        }
        composable(Route.SETTINGS.route) {
            SettingsScreen()
        }
    }
}