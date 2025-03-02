package com.example.nutritrack.data.model

data class AlimentoPersonalizadoResponse(
    val success: Boolean,
    val message: String,
    val data: AlimentoPersonalizado?,
    val error: String?
)

data class AlimentoPersonalizado(
    val id_alimento: Int,
    val incluirEnDesayuno: Boolean,
    val incluirEnAlmuerzo: Boolean,
    val incluirEnCena: Boolean,
    val nombre: String,
    val caloriasPorPorcion: Int,
    val proteinaPorPorcion: Int,
    val carbohidratosPorPorcion: Int,
    val grasaPorPorcion: Int,
    val unidadPorcion: String,
    val id_usuario: Int,
    val es_personalizado: Boolean
)
