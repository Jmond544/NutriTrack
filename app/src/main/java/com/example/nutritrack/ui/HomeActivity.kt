package com.example.nutritrack.ui

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPagerWeeks = findViewById(R.id.viewPagerWeeks)
        weekPagerAdapter = WeekPagerAdapter()

        viewPagerWeeks.adapter = weekPagerAdapter

        // Iniciar en la semana actual (centro de la lista de semanas)
        viewPagerWeeks.setCurrentItem(Int.MAX_VALUE / 2, false)
    }
}