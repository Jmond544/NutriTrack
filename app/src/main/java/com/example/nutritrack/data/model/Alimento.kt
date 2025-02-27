package com.example.nutritrack.data.model

data class Alimento(
    val id_alimento: Int? = null,  // Puede ser nulo si se genera automáticamente
    val incluirEnDesayuno: Boolean,
    val incluirEnAlmuerzo: Boolean,
    val incluirEnCena: Boolean,
    val nombre: String,
    val caloriasPorPorcion: Double,
    val proteinaPorPorcion: Double,
    val carbohidratosPorPorcion: Double,
    val grasaPorPorcion: Double,
    val unidadPorcion: String,
    val es_personalizado: Boolean
)
