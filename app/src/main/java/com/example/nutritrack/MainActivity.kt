package com.example.nutritrack

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nutritrack.data.repository.UsuarioRepository
import com.example.nutritrack.ui.HomeActivity
import com.example.nutritrack.ui.RegisterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextMail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textRegisterLink: TextView
    private lateinit var textErrorMessage: TextView

    private val usuarioRepository = UsuarioRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular elementos del layout
        editTextMail = findViewById(R.id.editTextMail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textErrorMessage = findViewById(R.id.textErrorMessage)
        textRegisterLink = findViewById(R.id.textRegisterLink)

        buttonLogin.setOnClickListener {
            iniciarSesion()
        }

        textRegisterLink.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iniciarSesion() {
        val mail = editTextMail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Iniciando sesión con mail: $mail")

        // Deshabilitar botón y cambiar texto a "Ingresando..."
        buttonLogin.isEnabled = false
        buttonLogin.text = "Ingresando..."
        textErrorMessage.text = ""

        usuarioRepository.iniciarSesion(mail, password) { response ->
            runOnUiThread {
                // Restaurar el botón
                buttonLogin.isEnabled = true
                buttonLogin.text = "Ingresar"

                if (response != null) {
                    Log.d(TAG, "Respuesta recibida: $response")
                    if (response.success) {
                        Toast.makeText(this, "Bienvenido ${response.data?.usuario?.nombre}!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra("nombre_usuario", response.data?.usuario?.nombre)
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Error en login: ${response.message}")
                        textErrorMessage.text = response.message
                    }
                } else {
                    Log.e(TAG, "Respuesta nula de la API")
                    textErrorMessage.text = "Error al iniciar sesión"
                }
            }
        }
    }
}