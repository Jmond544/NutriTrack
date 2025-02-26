package com.example.nutritrack.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nutritrack.R
import com.example.nutritrack.data.model.Usuario
import com.example.nutritrack.data.repository.UsuarioRepository
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private val usuarioRepository = UsuarioRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var nivelActividadSelected = ""
        var objetivoPesoSelected = ""

        // Conectar elementos del layout
        val editTextNombre = findViewById<TextInputEditText>(R.id.editTextNombre)
        val editTextEdad = findViewById<TextInputEditText>(R.id.editTextEdad)
        val editTextMail = findViewById<TextInputEditText>(R.id.editTextMail)
        val editTextPassword = findViewById<TextInputEditText>(R.id.editTextPassword)
        val editTextPeso = findViewById<TextInputEditText>(R.id.editTextPeso)
        val editTextAltura = findViewById<TextInputEditText>(R.id.editTextAltura)
        val autoCompleteGenero = findViewById<MaterialAutoCompleteTextView>(R.id.autoCompleteGenero)
        val sliderNivelActividad = findViewById<Slider>(R.id.sliderNivelActividad)
        val sliderObjetivoPeso = findViewById<Slider>(R.id.sliderObjetivoPeso)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val textErrorMessage = findViewById<TextView>(R.id.textErrorMessage)

        // Lista de nombres de los niveles de actividad
        val nivelesActividad = listOf("Sedentario", "Ligero", "Moderado", "Activo", "Muy Activo")
        val objetivosPeso = listOf("Bajar peso", "Mantener peso", "Subir peso")

        // Configurar selector de género
        val generos = listOf("Masculino", "Femenino", "Otro")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, generos)
        autoCompleteGenero.setAdapter(adapter)

        sliderNivelActividad.addOnChangeListener { _, value, _ ->
            nivelActividadSelected = nivelesActividad[value.toInt()]
        }

        sliderObjetivoPeso.addOnChangeListener { _, value, _ ->
            objetivoPesoSelected = objetivosPeso[value.toInt()]
        }

        autoCompleteGenero.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                autoCompleteGenero.showDropDown()
            }
        }

        autoCompleteGenero.setOnClickListener {
            autoCompleteGenero.showDropDown()
        }

        // Acción al presionar "Registrarse"
        buttonRegister.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val edad = editTextEdad.text.toString().toIntOrNull() ?: 0
            val mail = editTextMail.text.toString()
            val password = editTextPassword.text.toString()
            val peso = editTextPeso.text.toString().toFloatOrNull() ?: 0f
            val altura = editTextAltura.text.toString().toFloatOrNull() ?: 0f
            val genero = autoCompleteGenero.text.toString()
            val nivelActividad = nivelesActividad[sliderNivelActividad.value.toInt()]
            val objetivoPeso = objetivosPeso[sliderObjetivoPeso.value.toInt()]

            // Validación de campos
            if (nombre.isEmpty() || mail.isEmpty() || password.isEmpty() || genero.isEmpty()) {
                textErrorMessage.text = "Por favor, completa todos los campos obligatorios."
                textErrorMessage.visibility = TextView.VISIBLE
                return@setOnClickListener
            }

            val tmb = if (genero == "Masculino") {
                88.36 + (13.4 * peso) + (4.8 * altura) - (5.7 * edad)
            } else {
                447.6 + (9.2 * peso) + (3.1 * altura) - (4.3 * edad)
            }

            val factorActividad = when (nivelActividad) {
                "Sedentario" -> 1.2
                "Ligero" -> 1.375
                "Moderado" -> 1.55
                "Activo" -> 1.725
                "Muy Activo" -> 1.9
                else -> 1.2
            }

            val objetivoCalorico = when (objetivoPeso) {
                "Bajar peso" -> tmb * factorActividad - 500
                "Subir peso" -> tmb * factorActividad + 500
                else -> tmb * factorActividad
            }

            val nuevoUsuario = Usuario(
                nombre = nombre,
                edad = edad,
                mail = mail,
                password = password,
                peso = peso,
                altura = altura,
                genero = genero,
                nivel_actividad = nivelActividad,
                objetivo_calorico = objetivoCalorico.toInt()
            )

            // Enviar solicitud de registro
            usuarioRepository.registrarUsuario(nuevoUsuario) { response ->
                runOnUiThread {
                    if (response?.success == true) {
                        Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_LONG).show()
                        finish() // Cerrar la actividad y volver a login
                    } else {
                        textErrorMessage.text = response?.message
                        textErrorMessage.visibility = TextView.VISIBLE
                    }
                }
            }
        }
    }
}