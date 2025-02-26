package com.example.nutritrack.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritrack.R

class CalendarAdapter(
    private val days: MutableList<CalendarItem>, // Cambiamos a MutableList para modificarlo
    private val onDaySelected: (Int) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {

    inner class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textDay: TextView = view.findViewById(R.id.textDay)
        private val textDayNumber: TextView = view.findViewById(R.id.textDayNumber)

        fun bind(item: CalendarItem, position: Int) {
            textDay.text = item.dayOfWeek
            textDayNumber.text = item.day.toString()

            // Cambiar color si está seleccionado
            val backgroundColor = if (item.isSelected) {
                ContextCompat.getColor(itemView.context, R.color.selected_day)
            } else {
                ContextCompat.getColor(itemView.context, android.R.color.transparent)
            }

            itemView.setBackgroundColor(backgroundColor)

            itemView.setOnClickListener {
                onDaySelected(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days[position], position)
    }

    override fun getItemCount() = days.size

    // Nueva función para obtener la lista de días
    fun getDays(): MutableList<CalendarItem> = days
}