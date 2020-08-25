package com.example.alarmz.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.alarmz.R
import java.lang.String
import java.util.concurrent.TimeUnit


class TimerFragment : Fragment() {

    companion object {
        fun newInstance() = TimerFragment()

        @JvmStatic
        lateinit var displayTimeText: TextView

        @JvmStatic
        var timerRunning = false
    }

    lateinit var startTimerButton: Button
    lateinit var resetTimerButton: Button
    var pauseTime = 0L
    var paused = true
    var timeThread = TimerThread()
    lateinit var hours: EditText
    lateinit var minutes: EditText
    lateinit var seconds: EditText
    lateinit var timeSetInfo: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_timer, container, false)

        startTimerButton = root.findViewById(R.id.startTimerT)
        resetTimerButton = root.findViewById(R.id.resetTimerT)
        displayTimeText = root.findViewById(R.id.timeDisplayT)

        hours = root.findViewById(R.id.timerHours)
        minutes = root.findViewById(R.id.timerMinutes)
        seconds = root.findViewById(R.id.timerSeconds)
        timeSetInfo = root.findViewById(R.id.timerSetTo)

        startTimerButton.setOnClickListener {
            if (timerRunning) {
                startTimerButton.text = "Resume"
                StartTimer()
            } else {
                var h = if (hours.text.toString() != "") {
                    hours.text.toString().toLong()
                } else 0L

                var m = if (minutes.text.toString() != "") {
                    minutes.text.toString().toLong()
                } else 0L

                var s = if (seconds.text.toString() != "") {
                    seconds.text.toString().toLong()
                } else 0L

                if ((m < 60) && (m >= 0) && (s < 60) && (s >= 0)) {
                    if(displayTimeText.text=="00:00:00") {
                        if (!((h == 0L) && (m == 0L) && (s == 0L))) {
                            timeSetInfo.text = "Timer set for $h hours $m minutes and $s seconds"
                        }
                        TimerCanvas.timerDuration =
                            (s * 1000) + (m * 1000 * 60) + (h * 60 * 1000 * 60)
                    }
                    startTimerButton.text = "Pause"
                    StartTimer()
                } else {
                    Toast.makeText(root.context, "Enter valid time duration", Toast.LENGTH_SHORT).show()
                }
            }
        }

        resetTimerButton.setOnClickListener {
            ResetTimer()
        }

        return root
    }

    private fun ResetTimer() {
        timerRunning = false
        TimerCanvas.start = false
        TimerCanvas.secondX = 0F
        TimerCanvas.secondY = -160F
        TimerCanvas.minuteX = 0F
        TimerCanvas.minuteY = -30F
        TimerCanvas.startTime = 0
        pauseTime = 0
        stopThread()
        displayTimeText.text = "00:00:00"
        startTimerButton.text = "Start"
    }

    private fun StartTimer() {
        if (timerRunning) {
            stopThread()
            timerRunning = false
            TimerCanvas.start = false
            pauseTime = System.currentTimeMillis()

        } else {
            startThread()
            if (TimerCanvas.startTime == 0L) {
                TimerCanvas.startTime = System.currentTimeMillis()
            } else {
                TimerCanvas.startTime += System.currentTimeMillis() - pauseTime
            }
            timerRunning = true
            TimerCanvas.start = true
        }
    }

    private fun startThread() {
        paused = false
        if (!timeThread.isAlive) {
            timeThread.start()
        }
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
                                        returnFormattedTimeString(
                                            TimerCanvas.timerDuration -
                                                    System.currentTimeMillis() + TimerCanvas.startTime
                                        )
                                }
                                if ((TimerCanvas.timerDuration -
                                            System.currentTimeMillis() + TimerCanvas.startTime) <= 0
                                ) {
                                    ResetTimer()
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