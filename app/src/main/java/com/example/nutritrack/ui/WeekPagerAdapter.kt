package com.example.nutritrack.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritrack.R
import java.util.*

class WeekPagerAdapter : RecyclerView.Adapter<WeekPagerAdapter.WeekViewHolder>() {

    inner class WeekViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val recyclerViewDays: RecyclerView = view.findViewById(R.id.recyclerViewDays)
        private lateinit var adapter: CalendarAdapter // Guarda una referencia al adapter

        fun bind(weekOffset: Int) {
            val days = generateWeekDays(weekOffset)

            // Seleccionar automáticamente el día actual si es la semana actual
            val calendar = Calendar.getInstance()
            val today = calendar.get(Calendar.DAY_OF_MONTH)
            days.forEach { it.isSelected = (weekOffset == 0 && it.day == today) }

            adapter = CalendarAdapter(days) { position ->
                updateSelection(position)
            }

            recyclerViewDays.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewDays.adapter = adapter
        }

        private fun updateSelection(position: Int) {
            val days = adapter.getDays() // Usamos la función en lugar de acceder directamente a la variable privada
            days.forEachIndexed { index, item ->
                item.isSelected = index == position
            }
            adapter.notifyItemRangeChanged(0, days.size) // Solo actualiza los elementos visibles
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_week, parent, false)
        return WeekViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        holder.bind(position - (Int.MAX_VALUE / 2)) // Centrar en la semana actual
    }

    override fun getItemCount() = Int.MAX_VALUE // Infinitas semanas

    private fun generateWeekDays(weekOffset: Int): MutableList<CalendarItem> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffset)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val daysOfWeek = listOf("L", "M", "M", "J", "V", "S", "D")
        val weekDays = mutableListOf<CalendarItem>()

        for (i in 0 until 7) {
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val dayOfWeek = daysOfWeek[i]
            weekDays.add(CalendarItem(day, dayOfWeek, false))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return weekDays
    }
}