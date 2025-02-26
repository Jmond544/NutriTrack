package com.example.nutritrack.data.api
import com.example.nutritrack.data.model.LoginRequest
import com.example.nutritrack.data.model.LoginResponse
import com.example.nutritrack.data.model.Usuario
import retrofit2.Call
import retrofit2.http.*

interface UsuarioApi {
    @GET("usuarios/{id}")
    fun obtenerUsuario(@Path("id") id: Int): Call<Usuario>

    @POST("usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("usuarios")
    fun crearUsuario(@Body usuario: Usuario): Call<LoginResponse>
}