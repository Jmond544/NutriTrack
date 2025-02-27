package com.example.nutritrack.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nutritrack.R
import com.google.android.material.textfield.TextInputEditText

class RegisterAlimentActivity : AppCompatActivity() {
    // Campos de texto (TextInputEditText dentro de TextInputLayout)
    private lateinit var etNombre: TextInputEditText
    private lateinit var etCalorias: TextInputEditText
    private lateinit var etProteinas: TextInputEditText
    private lateinit var etCarbohidratos: TextInputEditText
    private lateinit var etGrasas: TextInputEditText

    // Switches
    private lateinit var swDesayuno: Switch
    private lateinit var swAlmuerzo: Switch
    private lateinit var swCena: Switch

    // Botón para registrar
    private lateinit var btnRegistrar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_aliment)

        // Vinculación de vistas
        etNombre = findViewById(R.id.etNombre)
        etCalorias = findViewById(R.id.etCalorias)
        etProteinas = findViewById(R.id.etProteinas)
        etCarbohidratos = findViewById(R.id.etCarbohidratos)
        etGrasas = findViewById(R.id.etGrasas)

        swDesayuno = findViewById(R.id.swDesayuno)
        swAlmuerzo = findViewById(R.id.swAlmuerzo)
        swCena = findViewById(R.id.swCena)

        btnRegistrar = findViewById(R.id.btnRegistrar)

        // Acción del botón Registrar
        btnRegistrar.setOnClickListener { registrarAlimento() }
    }

    private fun registrarAlimento() {
        // Obtener y limpiar espacios de cada campo
        val nombre = etNombre.text?.toString()?.trim() ?: ""
        val caloriasText = etCalorias.text?.toString()?.trim() ?: ""
        val proteinasText = etProteinas.text?.toString()?.trim() ?: ""
        val carbohidratosText = etCarbohidratos.text?.toString()?.trim() ?: ""
        val grasasText = etGrasas.text?.toString()?.trim() ?: ""

        // Validar que ningún campo esté vacío
        if (nombre.isEmpty() || caloriasText.isEmpty() || proteinasText.isEmpty() ||
            carbohidratosText.isEmpty() || grasasText.isEmpty()
        ) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Convertir los textos a números
        val calorias = caloriasText.toIntOrNull()
        val proteinas = proteinasText.toIntOrNull()
        val carbohidratos = carbohidratosText.toIntOrNull()
        val grasas = grasasText.toIntOrNull()

        // Validar la conversión numérica
        if (calorias == null || proteinas == null || carbohidratos == null || grasas == null) {
            Toast.makeText(this, "Por favor, ingresa valores numéricos válidos", Toast.LENGTH_SHORT).show()
            return
        }

        val incluyeDesayuno = swDesayuno.isChecked
        val incluyeAlmuerzo = swAlmuerzo.isChecked
        val incluyeCena = swCena.isChecked

        // Mostrar la información registrada
        val mensaje = """
        Alimento registrado:
        Nombre: $nombre
        Calorías: $calorias g/porción
        Proteínas: $proteinas g/porción
        Carbohidratos: $carbohidratos g/porción
        Grasas: $grasas g/porción
        Incluir en desayuno: $incluyeDesayuno
        Incluir en almuerzo: $incluyeAlmuerzo
        Incluir en cena: $incluyeCena
    """.trimIndent()

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        limpiarCampos()
    }

    private fun limpiarCampos() {
        etNombre.text?.clear()
        etCalorias.text?.clear()
        etProteinas.text?.clear()
        etCarbohidratos.text?.clear()
        etGrasas.text?.clear()
        swDesayuno.isChecked = false
        swAlmuerzo.isChecked = false
        swCena.isChecked = false
    }
}