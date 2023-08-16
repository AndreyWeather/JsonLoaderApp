package com.example.jsonloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.jsonloader.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val viewModel: viewModel by viewModels()
    private val fragList = listOf(
        Fragment1.newInstance(1),
        )
    private val fragPagesTitles = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
        "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
        "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = viewPagerAdapter(this, fragList)
        binding.viewPager.adapter = adapter
        fun a():String {return "hi"}
        val viewPager = binding.viewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = fragPagesTitles[pos]
        }.attach()
    }
}