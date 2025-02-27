package com.example.nutritrack.data.model

data class ComidaRequest(
    val comida: Comida,
    val usuarioId: Int,
    val nombreAlimento: String,
    val cantidad: Int
)

data class Comida(
    val fecha: String,
    val tipo_comida: String
)