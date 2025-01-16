package com.carlosalcina.sharedpreferences.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.carlosalcina.sharedpreferences.repository.NotasRepository
import com.carlosalcina.sharedpreferences.repository.ThemeRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBody(noteRepository: NotasRepository, themeRepository: ThemeRepository, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    val notesFlow = remember { noteRepository.notes }
    val notes = notesFlow.collectAsState(initial = emptySet()).value

    val darkThemeFlow = remember { themeRepository.isDarkTheme }
    val darkThemeValue = darkThemeFlow.collectAsState(initial = false).value

    var texto = remember { mutableStateOf("") }
    var noteToEdit = remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notas App") },
                actions = {
                    ThemeButton(
                        isDarkTheme = darkThemeValue
                    ) {
                        coroutineScope.launch {
                            themeRepository.changeTheme()
                        }
                    }
                })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Campo de entrada para nueva nota o edición
            TextField(
                value = texto.value,
                onValueChange = { texto.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            // Botón para añadir o editar nota
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (texto.value.isNotBlank()) {
                            if (noteToEdit.value != null) {
                                noteRepository.editNote(noteToEdit.value!!, texto.value)
                            } else {
                                noteRepository.saveNote(texto.value)
                            }
                            texto.value = ""
                            noteToEdit.value = null
                        }
                    }
                },
                modifier = Modifier.width(150.dp)
            ) {
                Text(if (noteToEdit.value == null) "Añadir" else "Actualizar")
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
                        },
                        onEdit = {
                            noteToEdit.value = note.toString()
                            texto.value = note.toString()
                        }
                    )
                }
            }
        }
    }
}

