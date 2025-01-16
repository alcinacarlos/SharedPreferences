package com.carlosalcina.sharedpreferences.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.carlosalcina.sharedpreferences.repository.NotasRepository
import com.carlosalcina.sharedpreferences.repository.ThemeRepository
import com.carlosalcina.sharedpreferences.screens.MainScreen


@Composable
fun AppNavigation(notesRepository: NotasRepository, themeRepository: ThemeRepository) {
    val navControlador = rememberNavController()

    NavHost(navController = navControlador, startDestination = AppScreen.MainScreen.route) {
        composable(AppScreen.MainScreen.route) {
            MainScreen(notesRepository, themeRepository, navControlador)
        }
    }
}