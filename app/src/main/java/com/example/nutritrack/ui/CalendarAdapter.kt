package com.example.nutritrack.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritrack.R

class CalendarAdapter(
    private val days: List<CalendarItem>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtDay: TextView = view.findViewById(R.id.txtDay)
        private val txtDayOfWeek: TextView = view.findViewById(R.id.txtDayOfWeek)

        fun bind(dayItem: CalendarItem, position: Int) {
            txtDay.text = dayItem.day.toString()
            txtDayOfWeek.text = dayItem.dayOfWeek
            itemView.isSelected = dayItem.isSelected

            itemView.setBackgroundResource(
                if (dayItem.isSelected) android.R.color.holo_orange_light
                else android.R.color.transparent
            )

            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(days[position], position)
    }

    override fun getItemCount() = days.size
}