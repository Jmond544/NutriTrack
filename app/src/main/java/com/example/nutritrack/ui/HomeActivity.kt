package com.example.nutritrack.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nutritrack.R
import com.example.nutritrack.data.model.ComidaConDetalles
import com.example.nutritrack.data.repository.ComidaRepository

class HomeActivity : BaseActivity() {

    private lateinit var viewPagerWeeks: ViewPager2
    private lateinit var weekPagerAdapter: WeekPagerAdapter
    private lateinit var textSelectedDate: TextView
    private lateinit var textMonthYear: TextView
    private lateinit var buttonDesayuno: Button
    private lateinit var buttonAlmuerzo: Button
    private lateinit var buttonCena: Button
    private var selectedDate: CalendarItem? = null

    private lateinit var repository: ComidaRepository
    private lateinit var adapterDesayuno: ComidaAdapter
    private lateinit var adapterAlmuerzo: ComidaAdapter
    private lateinit var adapterCena: ComidaAdapter

    private lateinit var recyclerDesayuno: RecyclerView
    private lateinit var recyclerAlmuerzo: RecyclerView
    private lateinit var recyclerCena: RecyclerView

    private lateinit var textCaloriasTotales: TextView
    private var caloriasObjetivo: Int = 0

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_home
    }

    override fun onResume() {
        super.onResume()
        setSelectedNavItem(R.id.nav_home) // o R.id.nav_profile dependiendo de la actividad
        cargarComidas()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSelectedNavItem(R.id.nav_home)
        viewPagerWeeks = findViewById(R.id.viewPagerWeeks)
        textSelectedDate = findViewById(R.id.textSelectedDate)
        textMonthYear = findViewById(R.id.textMonthYear)
        buttonDesayuno = findViewById(R.id.buttonDesayuno)
        buttonAlmuerzo = findViewById(R.id.buttonAlmuerzo)
        buttonCena = findViewById(R.id.buttonCena)

        recyclerDesayuno = findViewById(R.id.recyclerDesayuno)
        recyclerAlmuerzo = findViewById(R.id.recyclerAlmuerzo)
        recyclerCena = findViewById(R.id.recyclerCena)
        textCaloriasTotales = findViewById(R.id.textCaloriasTotales)

        recyclerDesayuno.layoutManager = LinearLayoutManager(this)
        recyclerAlmuerzo.layoutManager = LinearLayoutManager(this)
        recyclerCena.layoutManager = LinearLayoutManager(this)


        repository = ComidaRepository()
        cargarComidas()

        weekPagerAdapter = WeekPagerAdapter(
            { selectedDate -> updateSelectedDate(selectedDate) },
            { monthYear -> updateMonthYear(monthYear) }
        )

        viewPagerWeeks.adapter = weekPagerAdapter
        viewPagerWeeks.setCurrentItem(Int.MAX_VALUE / 2, false)

        buttonDesayuno.setOnClickListener {
            goToRegisterFood("Desayuno")
        }

        buttonAlmuerzo.setOnClickListener {
            goToRegisterFood("Almuerzo")
        }

        buttonCena.setOnClickListener {
            goToRegisterFood("Cena")
        }
    }

    private fun updateSelectedDate(selectedDate: CalendarItem) {
        this.selectedDate = selectedDate
        textSelectedDate.text = "Día seleccionado: ${selectedDate.day}"
        cargarComidas()
    }

    private fun updateMonthYear(monthYear: String) {
        textMonthYear.text = monthYear
    }

    private fun goToRegisterFood(tipoComida: String) {
        selectedDate?.let {
            val fecha = "${it.year}-${it.month+1}-${it.day}"
            val intent = Intent(this, RegisterFoodActivity::class.java)
            intent.putExtra("fecha", fecha)
            intent.putExtra("tipoComida", tipoComida)
            startActivity(intent)
        }
    }

    private fun cargarComidas() {
        selectedDate?.let { fechaSeleccionada ->
            val fecha = String.format("%04d-%02d-%02d", fechaSeleccionada.year, fechaSeleccionada.month + 1, fechaSeleccionada.day)
            Log.d("HomeActivity", "Fecha seleccionada: $fecha")

            repository.listarComidas { comidas ->
                if (comidas != null) {
                    Log.d("HomeActivity", "Total comidas recibidas: ${comidas.size}")

                    val comidasFecha = comidas.filter { it.fecha == fecha }
                    Log.d("HomeActivity", "Comidas filtradas por fecha ($fecha): ${comidasFecha.size}")

                    val desayuno = comidasFecha.filter { it.tipo_comida == "Desayuno" }
                    val almuerzo = comidasFecha.filter { it.tipo_comida == "Almuerzo" }
                    val cena = comidasFecha.filter { it.tipo_comida == "Cena" }

                    Log.d("HomeActivity", "Desayuno: ${desayuno.size}, Almuerzo: ${almuerzo.size}, Cena: ${cena.size}")

                    adapterDesayuno = ComidaAdapter(desayuno)
                    adapterAlmuerzo = ComidaAdapter(almuerzo)
                    adapterCena = ComidaAdapter(cena)

                    recyclerDesayuno.adapter = adapterDesayuno
                    recyclerAlmuerzo.adapter = adapterAlmuerzo
                    recyclerCena.adapter = adapterCena

                    recyclerDesayuno.visibility = if (desayuno.isNotEmpty()) View.VISIBLE else View.GONE
                    recyclerAlmuerzo.visibility = if (almuerzo.isNotEmpty()) View.VISIBLE else View.GONE
                    recyclerCena.visibility = if (cena.isNotEmpty()) View.VISIBLE else View.GONE

                    // Calcular calorías totales
                    val caloriasTotales = calcularCaloriasTotales(comidasFecha)

                    if (comidasFecha.isNotEmpty() && comidasFecha[0].Usuarios.isNotEmpty()) {
                        caloriasObjetivo = comidasFecha[0].Usuarios[0].objetivo_calorico
                    }
                    textCaloriasTotales.text = "$caloriasTotales / $caloriasObjetivo kcal"
                } else {
                    Log.e("HomeActivity", "No se recibieron comidas")
                    recyclerDesayuno.visibility = View.GONE
                    recyclerAlmuerzo.visibility = View.GONE
                    recyclerCena.visibility = View.GONE
                    textCaloriasTotales.text = "Selecciona un día ppara comenzar"
                }
            }
        } ?: Log.e("HomeActivity", "Fecha seleccionada es nula")
    }

    fun calcularCaloriasTotales(comidas: List<ComidaConDetalles>): Double {
        var totalCalorias = 0.0

        comidas.forEach { comida ->
            comida.DetalleComidas.forEach { detalle ->
                val cantidad = detalle.cantidad.toDouble()
                val alimento = detalle.Alimento

                totalCalorias += (cantidad * alimento.caloriasPorPorcion) / 100
            }
        }

        return totalCalorias
    }
}