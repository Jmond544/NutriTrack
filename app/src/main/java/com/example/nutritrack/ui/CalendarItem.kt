package com.example.nutritrack.ui

data class CalendarItem(val day: Int,
                        val dayOfWeek: String,
                        var isSelected: Boolean = false,
                        val month: Int,
                        val year: Int )