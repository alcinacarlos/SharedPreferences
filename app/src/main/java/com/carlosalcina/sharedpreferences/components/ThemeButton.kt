package com.carlosalcina.sharedpreferences.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ThemeButton(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Button(
        onClick = onToggleTheme,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        shape = androidx.compose.foundation.shape.CircleShape
    ) {
        Icon(
            imageVector = if (isDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
            contentDescription = if (isDarkTheme) "Modo oscuro" else "Modo claro",
            modifier = androidx.compose.ui.Modifier.size(24.dp)
        )
    }
}