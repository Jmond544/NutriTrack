package com.example.nutritrack.data.repository

import android.util.Log
import com.example.nutritrack.data.api.ComidaApi
import com.example.nutritrack.data.model.ComidaRequest
import com.example.nutritrack.data.model.ComidaResponse
import com.example.nutritrack.data.model.ComidaConDetalles
import com.example.nutritrack.data.model.ListaComidaResponse
import com.example.nutritrack.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComidaRepository {
    private val comidaApi = RetrofitClient.create(ComidaApi::class.java)
    private val TAG = "ComidaRepository"

    // Método para crear una comida
    fun crearComida(request: ComidaRequest, callback: (ComidaResponse?) -> Unit) {
        comidaApi.crearComida(request).enqueue(object : Callback<ComidaResponse> {
            override fun onResponse(call: Call<ComidaResponse>, response: Response<ComidaResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e(TAG, "Error al crear comida: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ComidaResponse>, t: Throwable) {
                Log.e(TAG, "Fallo en la petición: ${t.message}")
                callback(null)
            }
        })
    }

    // Método para listar las comidas por usuario
    fun listarComidas(callback: (List<ComidaConDetalles>?) -> Unit) {
        comidaApi.listarComidasPorUsuario().enqueue(object : Callback<ListaComidaResponse> {
            override fun onResponse(call: Call<ListaComidaResponse>, response: Response<ListaComidaResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.data)
                } else {
                    Log.e(TAG, "Error al listar comidas: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ListaComidaResponse>, t: Throwable) {
                Log.e(TAG, "Fallo en la petición: ${t.message}")
                callback(null)
            }
        })
    }
}