package com.example.nutritrack.data.model

data class ComidaResponse(
    val success: Boolean,
    val message: String,
    val data: ComidaData?
)

data class ComidaData(
    val id_comida: Int,
    val fecha: String,
    val tipo_comida: String
)