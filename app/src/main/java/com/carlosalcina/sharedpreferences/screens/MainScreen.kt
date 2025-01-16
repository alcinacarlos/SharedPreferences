package com.carlosalcina.sharedpreferences.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.carlosalcina.sharedpreferences.components.MainBody
import com.carlosalcina.sharedpreferences.repository.NotasRepository
import com.carlosalcina.sharedpreferences.repository.ThemeRepository

@Composable
fun MainScreen(notesRepository: NotasRepository, themeRepository: ThemeRepository, navController: NavController) {
    MainBody(notesRepository, themeRepository, navController)
}