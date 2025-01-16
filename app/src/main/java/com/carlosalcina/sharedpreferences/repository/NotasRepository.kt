package com.carlosalcina.sharedpreferences.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.carlosalcina.sharedpreferences.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotasRepository(private val context: Context) {
    companion object {
        val NOTAS_KEY = stringSetPreferencesKey("notas_key")
    }

    private val dataStore = context.dataStore

    // Obtener todas las notas
    val notes: Flow<Set<String>> = dataStore.data
        .map { preferences ->
            preferences[NOTAS_KEY] ?: emptySet()
        }

    // Guardar una nueva nota
    suspend fun saveNote(note: String) {
        dataStore.edit { preferences ->
            val currentNotes = preferences[NOTAS_KEY] ?: emptySet()
            preferences[NOTAS_KEY] = currentNotes + note
        }
    }

    // Eliminar una nota
    suspend fun deleteNote(note: String) {
        dataStore.edit { preferences ->
            val currentNotes = preferences[NOTAS_KEY] ?: emptySet()
            preferences[NOTAS_KEY] = currentNotes - note
        }
    }

    // Editar una nota existente
    suspend fun editNote(oldNote: String, newNote: String) {
        dataStore.edit { preferences ->
            val currentNotes = preferences[NOTAS_KEY] ?: emptySet()
            if (currentNotes.contains(oldNote)) {
                preferences[NOTAS_KEY] = currentNotes - oldNote + newNote
            }
        }
    }
}

