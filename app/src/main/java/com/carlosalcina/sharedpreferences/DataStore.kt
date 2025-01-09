package com.carlosalcina.sharedpreferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "notas")

val NOTAS_KEY = stringSetPreferencesKey("notas_key")
val THEME_KEY = booleanPreferencesKey("theme_key")
