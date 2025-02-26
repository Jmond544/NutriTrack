package com.example.nutritrack.data.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData? = null,  // Solo está presente si el login es exitoso
    val error: String? = null     // Solo está presente si hay un error
)

data class LoginData(
    val usuario: Usuario?,
    val token: String?
)