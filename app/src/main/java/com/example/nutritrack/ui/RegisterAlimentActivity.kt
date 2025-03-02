package com.example.nutritrack.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nutritrack.R
import com.example.nutritrack.data.model.AlimentoPersonalizadoRequest
import com.example.nutritrack.data.model.AlimentoPersonalizadoResponse
import com.example.nutritrack.data.repository.AlimentoRepository
import com.google.android.material.textfield.TextInputEditText

class RegisterAlimentActivity : AppCompatActivity() {

    // Campos de texto
    private lateinit var etNombre: TextInputEditText
    private lateinit var etCalorias: TextInputEditText
    private lateinit var etProteinas: TextInputEditText
    private lateinit var etCarbohidratos: TextInputEditText
    private lateinit var etGrasas: TextInputEditText

    // Switches
    private lateinit var swDesayuno: Switch
    private lateinit var swAlmuerzo: Switch
    private lateinit var swCena: Switch

    // Botón
    private lateinit var btnRegistrar: Button

    // Repositorio
    private val alimentoRepository = AlimentoRepository()

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

        // Acción del botón
        btnRegistrar.setOnClickListener { registrarAlimento() }
    }

    private fun registrarAlimento() {
        val nombre = etNombre.text?.toString()?.trim() ?: ""
        val calorias = etCalorias.text?.toString()?.toIntOrNull()
        val proteinas = etProteinas.text?.toString()?.toIntOrNull()
        val carbohidratos = etCarbohidratos.text?.toString()?.toIntOrNull()
        val grasas = etGrasas.text?.toString()?.toIntOrNull()

        if (nombre.isEmpty() || calorias == null || proteinas == null || carbohidratos == null || grasas == null) {
            Toast.makeText(this, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        val request = AlimentoPersonalizadoRequest(
            incluirEnDesayuno = swDesayuno.isChecked,
            incluirEnAlmuerzo = swAlmuerzo.isChecked,
            incluirEnCena = swCena.isChecked,
            nombre = nombre,
            caloriasPorPorcion = calorias,
            proteinaPorPorcion = proteinas,
            carbohidratosPorPorcion = carbohidratos,
            grasaPorPorcion = grasas,
            unidadPorcion = "gramos" // Puedes ajustar la unidad según lo que necesites
        )

        alimentoRepository.crearAlimentoPersonalizado(request) { alimento ->
            if (alimento != null) {
                Toast.makeText(this, "Alimento registrado con éxito", Toast.LENGTH_LONG).show()
                limpiarCampos()
                finish()
            } else {
                Toast.makeText(this, "Error al registrar alimento", Toast.LENGTH_SHORT).show()
            }
        }
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