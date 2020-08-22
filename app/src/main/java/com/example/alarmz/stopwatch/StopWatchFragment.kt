package com.example.alarmz.stopwatch

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmz.R


class StopWatchFragment : Fragment() {

    companion object {

        fun newInstance() = StopWatchFragment()

        @JvmStatic
        lateinit var displayTimeText: Chronometer

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
        var elapsed = System.currentTimeMillis() - MyCanvas.startTime
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
        MyCanvas.start = false
        MyCanvas.secondX = 0F
        MyCanvas.secondY = -160F
        MyCanvas.minuteX = 0F
        MyCanvas.minuteY = -30F
        MyCanvas.startTime = 0
        pauseTime = 0
        laps.clear()
        populateRecycler()
        displayTimeText.base = SystemClock.elapsedRealtime()
        displayTimeText.stop()
    }

    private fun StartTimer() {
        if (timerRunning) {
            timerRunning = false
            MyCanvas.start = false
            pauseTime = System.currentTimeMillis()
            displayTimeText.stop()

        } else {
            if (MyCanvas.startTime == 0L) {
                MyCanvas.startTime = System.currentTimeMillis()
                displayTimeText.base = SystemClock.elapsedRealtime()
                displayTimeText.start()
            } else {
                displayTimeText.base =
                    SystemClock.elapsedRealtime() - (MyCanvas.currentTime - MyCanvas.startTime)
                displayTimeText.start()
                MyCanvas.startTime += System.currentTimeMillis() - pauseTime
            }
            timerRunning = true
            MyCanvas.start = true
        }
    }

}