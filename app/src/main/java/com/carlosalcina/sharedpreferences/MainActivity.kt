package com.carlosalcina.sharedpreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.carlosalcina.sharedpreferences.navigation.AppNavigation
import com.carlosalcina.sharedpreferences.repository.*
import com.carlosalcina.sharedpreferences.ui.theme.SharedPreferencesTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notesRepository = NotasRepository(this)
        val themeRepository = ThemeRepository(this)

        setContent {
            val darkThemeFlow = remember { themeRepository.isDarkTheme }
            val darkThemeValue = darkThemeFlow.collectAsState(initial = false).value

            SharedPreferencesTheme(darkTheme = darkThemeValue) {
                AppNavigation(notesRepository, themeRepository)
            }
        }
    }
}


