package com.example.nutritrack.data.repository

import android.util.Log
import com.example.nutritrack.data.api.AlimentoApi
import com.example.nutritrack.data.model.Alimento
import com.example.nutritrack.data.model.AlimentoPersonalizado
import com.example.nutritrack.data.model.AlimentoPersonalizadoRequest
import com.example.nutritrack.data.model.AlimentoPersonalizadoResponse
import com.example.nutritrack.data.model.AlimentoResponse
import com.example.nutritrack.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlimentoRepository {
    private val alimentoApi = RetrofitClient.create(AlimentoApi::class.java)
    private val TAG = "AlimentoRepository"

    fun obtenerAlimentos(callback: (List<Alimento>?) -> Unit) {
        alimentoApi.obtenerAlimentos().enqueue(object : Callback<AlimentoResponse> {
            override fun onResponse(call: Call<AlimentoResponse>, response: Response<AlimentoResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.data)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<AlimentoResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun crearAlimentoPersonalizado(request: AlimentoPersonalizadoRequest, callback: (AlimentoPersonalizado?) -> Unit) {
        alimentoApi.crearAlimento(request).enqueue(object : Callback<AlimentoPersonalizadoResponse> {
            override fun onResponse(
                call: Call<AlimentoPersonalizadoResponse>,
                response: Response<AlimentoPersonalizadoResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.data) // Devolver la data directamente
                } else {
                    callback(null) // En caso de error, devolver null
                }
            }

            override fun onFailure(call: Call<AlimentoPersonalizadoResponse>, t: Throwable) {
                callback(null) // Manejar el fallo
            }
        })
    }
}
