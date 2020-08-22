package com.example.alarmz.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import com.example.alarmz.R
import kotlinx.android.synthetic.main.activity_set_alarm.*
import java.util.*

class SetAlarmActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)

        calendar = Calendar.getInstance()

        button_timepicker.setOnClickListener {
            var timepicker = TimePickerFragment()
            timepicker.show(supportFragmentManager, "Time Picker")
        }

        setAlarmButton.setOnClickListener {
            setAlarm()
        }

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
    }

    private fun setAlarm() {
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var i = Intent(this, AlarmBroadcastReceiver::class.java)
        var  pendingIntent = PendingIntent.getBroadcast(this,1, i, 0)

        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
    }


}