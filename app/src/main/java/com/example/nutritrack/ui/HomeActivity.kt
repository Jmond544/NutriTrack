package com.example.nutritrack.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nutritrack.R
import java.util.Calendar

class HomeActivity : AppCompatActivity() {

    private lateinit var viewPagerWeeks: ViewPager2
    private lateinit var weekPagerAdapter: WeekPagerAdapter
    private lateinit var textSelectedDate: TextView
    private lateinit var textMonthYear: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPagerWeeks = findViewById(R.id.viewPagerWeeks)
        textSelectedDate = findViewById(R.id.textSelectedDate)
        textMonthYear = findViewById(R.id.textMonthYear)

        weekPagerAdapter = WeekPagerAdapter(
            { selectedDate -> updateSelectedDate(selectedDate) },
            { monthYear -> updateMonthYear(monthYear) }
        )

        viewPagerWeeks.adapter = weekPagerAdapter
        viewPagerWeeks.setCurrentItem(Int.MAX_VALUE / 2, false)
    }

    private fun updateSelectedDate(selectedDate: CalendarItem) {
        textSelectedDate.text = "Día seleccionado: ${selectedDate.day}"
    }

    private fun updateMonthYear(monthYear: String) {
        textMonthYear.text = monthYear
    }
}