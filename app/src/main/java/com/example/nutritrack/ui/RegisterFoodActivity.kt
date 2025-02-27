package com.example.nutritrack.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nutritrack.R
import com.example.nutritrack.data.model.Comida
import com.example.nutritrack.data.model.ComidaRequest
import com.example.nutritrack.data.repository.AlimentoRepository
import com.example.nutritrack.data.repository.ComidaRepository
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.util.*

class RegisterFoodActivity : AppCompatActivity() {

    private lateinit var spTipoComida: MaterialAutoCompleteTextView
    private lateinit var spAlimento: MaterialAutoCompleteTextView
    private lateinit var btnPersonalizado: Button
    private lateinit var etCantidad: EditText
    private lateinit var etFecha: EditText
    private lateinit var btnRegistrar: Button
    private val comidaRepository = ComidaRepository()

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

        // Recuperar los datos del Intent
        val fecha = intent.getStringExtra("fecha")
        val tipoComida = intent.getStringExtra("tipoComida")

        // Asignar la fecha al campo de texto si no es nulo
        fecha?.let {
            etFecha.setText(it)
        }

        // Asignar el tipo de comida al spinner si no es nulo
        tipoComida?.let {
            spTipoComida.setText(it, false)
        }

        // Mostrar dropdowns al hacer clic o tener foco
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
            val intent = Intent(this, RegisterAlimentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSpinners() {
        val tiposComida = listOf("Desayuno", "Almuerzo", "Cena")
        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposComida)
        spTipoComida.setAdapter(adapterTipo)

        val alimentoRepository = AlimentoRepository()
        alimentoRepository.obtenerAlimentos { alimentos ->
            if (alimentos != null) {
                val nombresAlimentos = alimentos.map { it.nombre }
                val adapterAlimentos = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombresAlimentos)
                spAlimento.setAdapter(adapterAlimentos)
            } else {
                Toast.makeText(this, "Error al cargar alimentos", Toast.LENGTH_SHORT).show()
            }
        }
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

        if (tipoComida.isEmpty() || alimento.isEmpty() || cantidad.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val usuarioId = 1 // Aquí deberías obtener el ID del usuario logueado (hardcodeado por ahora)
        val cantidadInt = cantidad.toIntOrNull()

        if (cantidadInt == null || cantidadInt <= 0) {
            Toast.makeText(this, "Cantidad no válida", Toast.LENGTH_SHORT).show()
            return
        }

        val request = ComidaRequest(
            Comida(fecha, tipoComida),
            usuarioId,
            alimento,
            cantidadInt
        )

        comidaRepository.crearComida(request) { response ->
            if (response != null) {
                Toast.makeText(this, "Comida registrada con éxito", Toast.LENGTH_LONG).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al registrar comida", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun limpiarCampos() {
        etCantidad.text.clear()
        etFecha.text.clear()
    }
}