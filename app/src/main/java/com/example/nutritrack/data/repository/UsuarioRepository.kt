package com.example.nutritrack.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.nutritrack.data.model.LoginRequest
import com.example.nutritrack.data.model.LoginResponse
import com.example.nutritrack.utils.RetrofitClient
import com.example.nutritrack.data.api.UsuarioApi
import com.example.nutritrack.data.model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioRepository {
    private val usuarioApi = RetrofitClient.create(UsuarioApi::class.java)

    fun iniciarSesion(mail: String, password: String, callback: (LoginResponse?) -> Unit) {
        val request = LoginRequest(mail, password)
        Log.d(TAG, "Enviando solicitud de login: $request")

        usuarioApi.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Respuesta exitosa: ${response.body()}")
                    callback(response.body())
                } else {
                    Log.e(TAG, "Error en la respuesta: Código ${response.code()}, Error: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "Error en la solicitud: ${t.message}", t)
                callback(null)
            }
        })
    }
    fun registrarUsuario(usuario: Usuario, callback: (LoginResponse?) -> Unit) {
        usuarioApi.crearUsuario(usuario).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}