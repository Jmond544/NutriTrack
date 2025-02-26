package com.example.nutritrack.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritrack.R
import java.util.Calendar

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalendarAdapter
    private val daysList = mutableListOf<CalendarItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recyclerViewCalendar)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        generateDays()

        adapter = CalendarAdapter(daysList) { position ->
            updateSelection(position)
        }
        recyclerView.adapter = adapter
    }

    private fun generateDays() {
        val calendar = Calendar.getInstance()
        val daysOfWeek = listOf("L", "M", "M", "J", "V", "S", "D")

        for (i in 0..6) {
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val dayOfWeek = daysOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]
            daysList.add(CalendarItem(day, dayOfWeek, i == 0)) // Marcar el primer día como seleccionado
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun updateSelection(position: Int) {
        daysList.forEachIndexed { index, dayItem ->
            dayItem.isSelected = index == position
        }
        adapter.notifyDataSetChanged()
    }
}