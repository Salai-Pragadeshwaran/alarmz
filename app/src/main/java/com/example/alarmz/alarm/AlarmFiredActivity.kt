package com.example.alarmz.alarm

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.alarmz.R
import kotlinx.android.synthetic.main.activity_alarm_fired.*

class AlarmFiredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_fired)

        var alarmSound = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        alarmSound.start()

        dismiss.setOnClickListener {
            alarmSound.stop()
            finish()
        }
    }
}