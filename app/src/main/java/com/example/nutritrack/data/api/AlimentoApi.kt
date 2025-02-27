package com.example.nutritrack.data.api

import com.example.nutritrack.data.model.AlimentoResponse
import retrofit2.Call
import retrofit2.http.GET

interface AlimentoApi {
    @GET("alimentos")
    fun obtenerAlimentos(): Call<AlimentoResponse>
}