package com.example.nutritrack.data.api

import com.example.nutritrack.data.model.ComidaRequest
import com.example.nutritrack.data.model.ComidaResponse
import com.example.nutritrack.data.model.ListaComidaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ComidaApi {

    @POST("comida")
    fun crearComida(
        @Body request: ComidaRequest
    ): Call<ComidaResponse>

    @GET("comida/lista-usuario")
    fun listarComidasPorUsuario(
    ): Call<ListaComidaResponse>

}