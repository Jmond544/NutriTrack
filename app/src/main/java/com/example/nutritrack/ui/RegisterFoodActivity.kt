package com.example.nutritrack.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nutritrack.R
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.util.*

class RegisterFoodActivity : AppCompatActivity() {

    private lateinit var spTipoComida: MaterialAutoCompleteTextView
    private lateinit var spAlimento: MaterialAutoCompleteTextView
    private lateinit var btnPersonalizado: Button
    private lateinit var etCantidad: EditText
    private lateinit var etFecha: EditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_food)

        // Inicializar vistas
        spTipoComida = findViewById(R.id.spTipoComida)
        spAlimento = findViewById(R.id.spAlimento)
        btnPersonalizado = findViewById(R.id.btnPersonalizado)
        etCantidad = findViewById(R.id.etCantidad)
        etFecha = findViewById(R.id.etFecha)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        // Cargar datos en los Spinners
        setupSpinners()

        spTipoComida.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                spTipoComida.showDropDown()
            }
        }
        spAlimento.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                spAlimento.showDropDown()
            }
        }

        spTipoComida.setOnClickListener {
            spTipoComida.showDropDown()
        }
        spAlimento.setOnClickListener {
            spAlimento.showDropDown()
        }

        // Evento para abrir el selector de fecha
        etFecha.setOnClickListener { showDatePicker() }

        // Evento de clic en el botón Registrar
        btnRegistrar.setOnClickListener { registrarComida() }

        // Evento para ingresar un alimento personalizado
        btnPersonalizado.setOnClickListener {
            Toast.makeText(this, "Función en desarrollo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinners() {
        val tiposComida = listOf("Desayuno", "Almuerzo", "Cena", "Snack")
        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposComida)
        spTipoComida.setAdapter(adapterTipo)

        val alimentos = listOf("Manzana", "Pan", "Pollo", "Arroz", "Leche")
        val adapterAlimentos = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, alimentos)
        spAlimento.setAdapter(adapterAlimentos)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year)
                etFecha.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun registrarComida() {
        val tipoComida = spTipoComida.text.toString()
        val alimento = spAlimento.text.toString()
        val cantidad = etCantidad.text.toString()
        val fecha = etFecha.text.toString()

        if (cantidad.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Comida registrada: $tipoComida, $alimento, $cantidad unidades, $fecha", Toast.LENGTH_LONG).show()
            limpiarCampos()
        }
    }

    private fun limpiarCampos() {
        etCantidad.text.clear()
        etFecha.text.clear()
    }
}