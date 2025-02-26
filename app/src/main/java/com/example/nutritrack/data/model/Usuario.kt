package com.example.nutritrack.data.model

data class Usuario(
    val id_usuario: Int? = null,  // Puede ser nulo si se genera automáticamente
    val nombre: String,
    val edad: Int,
    val mail: String,
    val password: String,
    val peso: Float,
    val altura: Float,
    val genero: String,
    val nivel_actividad: String,
    val objetivo_calorico: Int
)