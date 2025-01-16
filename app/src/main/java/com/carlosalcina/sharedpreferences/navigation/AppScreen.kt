package com.carlosalcina.sharedpreferences.navigation

sealed class AppScreen(val route: String) {
    object AddScreen: AppScreen("AddScreen")
    object EditScreen : AppScreen("EditScreen")
    object MainScreen : AppScreen("MainScreen")
}