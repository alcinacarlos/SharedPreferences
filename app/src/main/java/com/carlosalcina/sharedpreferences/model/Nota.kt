package com.carlosalcina.sharedpreferences.model

import java.time.LocalDate

data class Nota(
    val titulo: String,
    val texto: String,
    val fecha: LocalDate = LocalDate.now()
)
