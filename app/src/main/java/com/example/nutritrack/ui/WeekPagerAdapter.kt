package com.example.nutritrack.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritrack.R
import java.util.*

class WeekPagerAdapter(
    private val onDateSelected: (CalendarItem) -> Unit,    // Callback para día seleccionado
    private val onMonthYearChanged: (String) -> Unit       // Callback para mes y año
) : RecyclerView.Adapter<WeekPagerAdapter.WeekViewHolder>() {

    inner class WeekViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val recyclerViewDays: RecyclerView = view.findViewById(R.id.recyclerViewDays)
        private lateinit var adapter: CalendarAdapter

        fun bind(weekOffset: Int) {
            val days = generateWeekDays(weekOffset)

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.WEEK_OF_YEAR, weekOffset)

            val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            days.forEach { it.isSelected = (weekOffset == 0 && it.day == today) }

            adapter = CalendarAdapter(days) { position ->
                updateSelection(days, position)
            }

            recyclerViewDays.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewDays.adapter = adapter

            // Si hay un día seleccionado, actualizar el mes y año basado en ese día
            days.find { it.isSelected }?.let {
                onDateSelected(it)
                updateMonthYear(it)
            }
        }

        private fun updateSelection(days: List<CalendarItem>, position: Int) {
            days.forEachIndexed { index, item -> item.isSelected = index == position }
            adapter.notifyItemRangeChanged(0, days.size)

            val selectedDay = days[position]
            onDateSelected(selectedDay)
            updateMonthYear(selectedDay) // Actualizamos el mes y año cada vez que cambia el día
        }

        private fun updateMonthYear(selectedDay: CalendarItem) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, selectedDay.year)  // Asegurar que tomamos el año correcto
            calendar.set(Calendar.MONTH, selectedDay.month) // Asegurar que tomamos el mes correcto
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay.day)

            val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            val year = calendar.get(Calendar.YEAR)

            onMonthYearChanged("$month $year")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_week, parent, false)
        return WeekViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        holder.bind(position - (Int.MAX_VALUE / 2))
    }

    override fun getItemCount() = Int.MAX_VALUE

    private fun generateWeekDays(weekOffset: Int): MutableList<CalendarItem> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffset)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val daysOfWeek = listOf("L", "M", "M", "J", "V", "S", "D")
        val weekDays = mutableListOf<CalendarItem>()

        for (i in 0 until 7) {
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)  // Guardamos el mes correcto
            val year = calendar.get(Calendar.YEAR)    // Guardamos el año correcto
            val dayOfWeek = daysOfWeek[i]

            weekDays.add(CalendarItem(day, dayOfWeek, false, month, year))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return weekDays
    }
}