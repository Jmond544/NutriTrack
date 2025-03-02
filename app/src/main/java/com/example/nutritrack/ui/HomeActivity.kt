package com.example.nutritrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nutritrack.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class HomeActivity : BaseActivity() {

    private lateinit var viewPagerWeeks: ViewPager2
    private lateinit var weekPagerAdapter: WeekPagerAdapter
    private lateinit var textSelectedDate: TextView
    private lateinit var textMonthYear: TextView
    private lateinit var buttonDesayuno: Button
    private lateinit var buttonAlmuerzo: Button
    private lateinit var buttonCena: Button
    private var selectedDate: CalendarItem? = null

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_home
    }

    override fun onResume() {
        super.onResume()
        setSelectedNavItem(R.id.nav_home) // o R.id.nav_profile dependiendo de la actividad
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
}