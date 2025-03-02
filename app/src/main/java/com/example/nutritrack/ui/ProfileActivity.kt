package com.example.nutritrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.nutritrack.R
import com.example.nutritrack.data.model.Usuario
import com.example.nutritrack.data.repository.UsuarioRepository
import com.example.nutritrack.utils.PreferencesManager

class ProfileActivity : BaseActivity() {

    private lateinit var textNombre: TextView
    private lateinit var textEmail: TextView
    private lateinit var textEdad: TextView
    private lateinit var textPeso: TextView
    private lateinit var textAltura: TextView
    private lateinit var textGenero: TextView
    private lateinit var textNivelActividad: TextView
    private lateinit var textObjetivoCalorico: TextView
    private val usuarioRepository = UsuarioRepository()
    private lateinit var prefs: PreferencesManager

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_profile
    }

    override fun onResume() {
        super.onResume()
        setSelectedNavItem(R.id.nav_profile) // Marca el botón activo en la navegación
        cargarPerfil() // Aquí hacemos la petición cada vez que se vuelve a visualizar la página
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textNombre = findViewById(R.id.textNombre)
        textEmail = findViewById(R.id.textEmail)
        textEdad = findViewById(R.id.textEdad)
        textPeso = findViewById(R.id.textPeso)
        textAltura = findViewById(R.id.textAltura)
        textGenero = findViewById(R.id.textGenero)
        textNivelActividad = findViewById(R.id.textNivelActividad)
        textObjetivoCalorico = findViewById(R.id.textObjetivoCalorico)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        prefs = PreferencesManager(this)

        btnLogout.setOnClickListener {
            prefs.clearToken()
            Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finaliza la actividad actual para evitar regresar con el botón atrás
        }
    }

    private fun cargarPerfil() {
        usuarioRepository.getProfile { usuario ->
            runOnUiThread {
                if (usuario != null) {
                    textNombre.text = "Nombre: ${usuario.nombre}"
                    textEmail.text = "Correo: ${usuario.mail}"
                    textEdad.text = "Edad: ${usuario.edad}"
                    textPeso.text = "Peso: ${usuario.peso} kg"
                    textAltura.text = "Altura: ${usuario.altura} cm"
                    textGenero.text = "Género: ${usuario.genero}"
                    textNivelActividad.text = "Actividad: ${usuario.nivel_actividad}"
                    textObjetivoCalorico.text = "Calorías: ${usuario.objetivo_calorico} kcal"
                } else {
                    textNombre.text = "No se pudo cargar el perfil"
                }
            }
        }
    }
}