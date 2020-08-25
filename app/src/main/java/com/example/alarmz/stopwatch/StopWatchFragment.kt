package com.example.alarmz.stopwatch

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmz.MainActivity
import com.example.alarmz.R
import java.lang.String
import java.util.concurrent.TimeUnit


class StopWatchFragment : Fragment() {

    companion object {

        fun newInstance() = StopWatchFragment()

        @JvmStatic
        lateinit var displayTimeText: TextView

        @JvmStatic
        var timerRunning = false
    }


    var pauseTime = 0L
    lateinit var startTimerButton: Button
    lateinit var resetTimerButton: Button
    lateinit var lapButton: Button
    var laps = ArrayList<Lap>()
    lateinit var lapRecyclerView: RecyclerView
    lateinit var lapAdapter: LapAdapter
    var timeThread = TimerThread()
    var paused = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_stop_watch, container, false)

        startTimerButton = root.findViewById(R.id.startTimer)
        resetTimerButton = root.findViewById(R.id.resetTImer)
        lapButton = root.findViewById(R.id.lap)
        displayTimeText = root.findViewById(R.id.timeDisplay)

        lapRecyclerView = root.findViewById(R.id.lapRecycler)

        startTimerButton.setOnClickListener {
            if (timerRunning) {
                startTimerButton.text = "Resume"
            } else {
                startTimerButton.text = "Pause"
            }
            StartTimer()
        }

        resetTimerButton.setOnClickListener {
            startTimerButton.text = "Start"
            ResetTimer()
        }

        lapButton.setOnClickListener {
            if (timerRunning) {
                addLap()
            }
        }

        return root
    }

    private fun addLap() {
        var num: Int = 0;
        var elapsed = System.currentTimeMillis() - ClockCanvas.startTime
        var difference: Long = 0
        if (laps.size == 0) {
            num = 1
            difference = elapsed
        } else {
            num = laps[laps.size - 1].lapNo + 1
            difference = elapsed - laps[laps.size - 1].elapsedTime
        }
        laps.add(Lap(lapNo = num, elapsedTime = elapsed, timeDifference = difference))
        populateRecycler()
    }

    private fun populateRecycler() {
        lapAdapter = LapAdapter(laps)
        lapRecyclerView.adapter = lapAdapter
    }

    private fun ResetTimer() {
        timerRunning = false
        ClockCanvas.start = false
        ClockCanvas.secondX = 0F
        ClockCanvas.secondY = -160F
        ClockCanvas.minuteX = 0F
        ClockCanvas.minuteY = -30F
        ClockCanvas.startTime = 0
        pauseTime = 0
        laps.clear()
        populateRecycler()
        stopThread()
        displayTimeText.text = "00:00:00"
    }

    private fun StartTimer() {
        if (timerRunning) {
            stopThread()
            timerRunning = false
            ClockCanvas.start = false
            pauseTime = System.currentTimeMillis()

        } else {
            startThread()
            if (ClockCanvas.startTime == 0L) {
                ClockCanvas.startTime = System.currentTimeMillis()
            } else {
                ClockCanvas.startTime += System.currentTimeMillis() - pauseTime
            }
            timerRunning = true
            ClockCanvas.start = true
        }
    }

    private fun startThread() {
        paused = false
        timeThread.start()
    }

    private fun stopThread() {
        paused = true
    }

    inner class TimerThread() : Thread() {
        override fun run() {
            while (true) {
                try {
                    sleep(10)
                   activity!!.runOnUiThread(
                        object : Runnable {
                            override fun run() {
                                if (!paused) {
                                    displayTimeText.text =
                                        returnFormattedTimeString(System.currentTimeMillis() - ClockCanvas.startTime)
                                }
                            }
                        }
                    )

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        private fun returnFormattedTimeString(l: Long): kotlin.String {
            var hr = TimeUnit.MILLISECONDS.toHours(l)
            var min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr))
            var sec = TimeUnit.MILLISECONDS.toSeconds(
                l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min)
            )
            var ms = TimeUnit.MILLISECONDS.toMillis(
                l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(
                    sec
                )
            )
            ms /= 10
            return String.format("%02d:%02d.%02d", min, sec, ms)
        }
    }


}