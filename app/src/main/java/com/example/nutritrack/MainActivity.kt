package com.example.nutritrack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nutritrack.data.repository.UsuarioRepository
import com.example.nutritrack.ui.HomeActivity
import com.example.nutritrack.ui.LoginActivity
import com.example.nutritrack.utils.PreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: PreferencesManager
    private val usuarioRepository = UsuarioRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferencesManager(this)

        val token = prefs.getToken()

        if (token != null) {
            usuarioRepository.verificarToken { isValid ->
                if (isValid) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    prefs.clearToken() // Borra el token si no es válido
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}