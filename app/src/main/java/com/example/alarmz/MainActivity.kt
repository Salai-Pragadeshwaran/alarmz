package com.example.alarmz

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    companion object
    {
        @JvmStatic
        lateinit var appl: Application
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appl = application

        viewPager2.adapter = PageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager2)
    }
}