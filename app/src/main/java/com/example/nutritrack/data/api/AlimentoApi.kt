package com.example.nutritrack.data.api

import com.example.nutritrack.data.model.AlimentoPersonalizadoRequest
import com.example.nutritrack.data.model.AlimentoPersonalizadoResponse
import com.example.nutritrack.data.model.AlimentoResponse
import com.example.nutritrack.data.model.ComidaRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlimentoApi {
    @GET("alimentos")
    fun obtenerAlimentos(): Call<AlimentoResponse>

    @POST("alimentos")
    fun crearAlimento(
        @Body request:AlimentoPersonalizadoRequest
    ): Call<AlimentoPersonalizadoResponse>
}