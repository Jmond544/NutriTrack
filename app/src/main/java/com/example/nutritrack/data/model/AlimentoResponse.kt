package com.example.nutritrack.data.model

data class AlimentoResponse(
    val success: Boolean,
    val message: String,
    val data: List<Alimento>?
)