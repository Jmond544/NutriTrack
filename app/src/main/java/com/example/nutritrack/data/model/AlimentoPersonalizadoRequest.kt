package com.example.nutritrack.data.model

data class AlimentoPersonalizadoRequest(
    val incluirEnDesayuno: Boolean,
    val incluirEnAlmuerzo: Boolean,
    val incluirEnCena: Boolean,
    val nombre: String,
    val caloriasPorPorcion: Int,
    val proteinaPorPorcion: Int,
    val carbohidratosPorPorcion: Int,
    val grasaPorPorcion: Int,
    val unidadPorcion: String
)