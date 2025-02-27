package com.example.nutritrack.data.model

data class ListaComidaResponse(
    val success: Boolean,
    val message: String,
    val data: List<ComidaConDetalles>?
)

data class ComidaConDetalles(
    val id_comida: Int,
    val fecha: String,
    val tipo_comida: String,
    val Usuarios: List<Usuario>,
    val DetalleComidas: List<DetalleComida>
)

data class DetalleComida(
    val id_detalle: Int,
    val id_comida: Int,
    val id_alimento: Int,
    val cantidad: Int,
    val Alimento: Alimento
)