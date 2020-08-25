package com.example.alarmz.alarm

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.net.toUri
import com.example.alarmz.MainActivity
import com.example.alarmz.R
import kotlinx.android.synthetic.main.activity_alarm_fired.*
import kotlin.random.Random

class AlarmFiredActivity : AppCompatActivity() {

    var a: Int = 0
    var b: Int = 0
    var ans: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_fired)

        var alarmDatabase = AlarmDatabase.get(application)

        var rq = intent.getIntExtra("REQUEST_CODE", -1)
        var uri: Uri = Uri.parse(alarmDatabase.getAlarmDao().getAlarm(rq).ringtone)

        var alarmSound = MediaPlayer()
        try {
            alarmSound.setDataSource(applicationContext, uri)
        } catch (e: Exception) {
            alarmSound.setDataSource(applicationContext, Settings.System.DEFAULT_RINGTONE_URI)
        }
        alarmSound.prepare()
        alarmSound.start()

        a = (1..99).random()
        b = (1..99).random()

        question.text = "What is the sum of $a and $b"

        dismiss.setOnClickListener {
            ans = answer.text.toString().toInt()
            if(ans==(a+b)) {
                alarmSound.stop()
                finish()
            }else{
                answer.text = null
                Toast.makeText(this, "Wrong answer, Try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}