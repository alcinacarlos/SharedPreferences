package com.carlosalcina.sharedpreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosalcina.sharedpreferences.components.NoteItem
import com.carlosalcina.sharedpreferences.components.ThemeButton
import com.carlosalcina.sharedpreferences.repository.*
import com.carlosalcina.sharedpreferences.ui.theme.SharedPreferencesTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = NotasRepository(this)
        val themeRepository = ThemeRepository(this)

        setContent {
            val darkThemeFlow = remember { themeRepository.isDarkTheme }
            val darkThemeValue = darkThemeFlow.collectAsState(initial = false).value

            SharedPreferencesTheme(darkTheme = darkThemeValue) {
                NotesApp(repository, themeRepository)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp(noteRepository: NotasRepository, themeRepository: ThemeRepository) {
    val coroutineScope = rememberCoroutineScope()

    val notesFlow = remember { noteRepository.notes }
    val notes = notesFlow.collectAsState(initial = emptySet()).value

    val darkThemeFlow = remember { themeRepository.isDarkTheme }
    val darkThemeValue = darkThemeFlow.collectAsState(initial = false).value

    var texto = remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notas App") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Column {
                ThemeButton(
                    isDarkTheme = darkThemeValue
                ) {
                    coroutineScope.launch {
                        themeRepository.changeTheme()
                    }
                }
            }
            // Campo de entrada para nueva nota
            TextField(
                value = texto.value,
                onValueChange = { texto.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            // Botón para añadir nota
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (texto.value.isNotBlank()) {
                            noteRepository.saveNote(texto.value)
                            texto.value = ""
                        }
                    }
                },
                modifier = Modifier.width(300.dp)
            ) {
                Text("Añadir")
            }


            // Lista de notas
            LazyColumn {
                items(notes.toList()) { note ->
                    NoteItem(
                        note = note.toString(),
                        onDelete = {
                            coroutineScope.launch {
                                noteRepository.deleteNote(note.toString())
                            }
                        }
                    )
                }
            }
        }
    }
}

