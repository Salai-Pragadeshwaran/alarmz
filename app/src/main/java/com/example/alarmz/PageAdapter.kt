package com.example.alarmz

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.alarmz.alarm.AlarmFragment
import com.example.alarmz.stopwatch.StopWatchFragment
import com.example.alarmz.timer.TimerFragment
import kotlin.concurrent.timer

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
            when(position){
                0 -> { return AlarmFragment()}
                1 -> { return StopWatchFragment()}
                2 -> { return TimerFragment()}
                else -> { return AlarmFragment()}
            }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> { return "Alarm"}
            1 -> { return "Stop Watch"}
            2 -> { return "Timer"}
        }
        return super.getPageTitle(position)
    }

    override fun getCount(): Int {
        return 3
    }
}