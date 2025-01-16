package com.carlosalcina.sharedpreferences.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.carlosalcina.sharedpreferences.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ThemeRepository(private val context: Context) {
    companion object{
        val THEME_KEY = booleanPreferencesKey("theme_key")
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false
    }
    // Obtener el tema actual
    suspend fun getTheme(): Boolean {
        return isDarkTheme.first()
    }

    // Cambiar el tema
    suspend fun changeTheme() {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = !getTheme()
        }
    }
}

