package com.example.alarmz.alarm

import android.R.attr
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmz.MainActivity
import com.example.alarmz.R
import kotlinx.android.synthetic.main.activity_set_alarm.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class SetAlarmActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    lateinit var calendar: Calendar
    lateinit var alarmDatabase: AlarmDatabase
    var hour: Int = 0
    var minute: Int = 0
    var requestCode: Int = 0
    lateinit var monday: TextView
    lateinit var tuesday: TextView
    lateinit var wednesday: TextView
    lateinit var thursday: TextView
    lateinit var friday: TextView
    lateinit var saturday: TextView
    lateinit var sunday: TextView
    lateinit var alarm: AlarmInfo
    var i = Settings.System.DEFAULT_RINGTONE_URI.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)

        monday = findViewById(R.id.mon)
        tuesday = findViewById(R.id.tue)
        wednesday = findViewById(R.id.wed)
        thursday = findViewById(R.id.thu)
        friday = findViewById(R.id.fri)
        saturday = findViewById(R.id.sat)
        sunday = findViewById(R.id.sun)

        hour = intent.getIntExtra("HOUR", 10)
        minute = intent.getIntExtra("MINUTE", 20)
        requestCode = intent.getIntExtra("REQUEST_CODE", 0)


        alarmDatabase = AlarmDatabase.get(MainActivity.appl)
        alarm = alarmDatabase.getAlarmDao().getAlarm(requestCode)
        i = alarm.ringtone

        setWeekColor(monday, alarm.mon, -1)
        setWeekColor(tuesday, alarm.tue, -1)
        setWeekColor(wednesday, alarm.wed, -1)
        setWeekColor(thursday, alarm.thu, -1)
        setWeekColor(friday, alarm.fri, -1)
        setWeekColor(saturday, alarm.sat, -1)
        setWeekColor(sunday, alarm.sun, -1)

        var cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        alarmSetTime.text = updateAlarmSetTime(cal)


        calendar = Calendar.getInstance()

        button_timepicker.setOnClickListener {
            var timepicker = TimePickerFragment()
            timepicker.show(supportFragmentManager, "Time Picker")
        }

        setAlarmButton.setOnClickListener {
            setAlarm()
            alarmDatabase.getAlarmDao().updateAlarmTime(
                requestCode,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
            )
            alarmSetTime.text = updateAlarmSetTime(calendar)
        }

        ringtone.setOnClickListener {
            val intent1 = Intent()
            intent1.action = Intent.ACTION_GET_CONTENT
            intent1.type = "audio/*"
            intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(Intent.createChooser(intent1, "Choose Sound File"), 5)
        }

        monday.setOnClickListener {
            setWeekColor(monday, !alarm.mon, alarm.reqCode + 1)
            alarmDatabase.getAlarmDao().updateMonRepeat(!alarm.mon, (alarm.reqCode))
        }
        tuesday.setOnClickListener {
            setWeekColor(tuesday, !alarm.tue, alarm.reqCode + 2)
            alarmDatabase.getAlarmDao().updateTueRepeat(!alarm.tue, (alarm.reqCode))
        }
        wednesday.setOnClickListener {
            setWeekColor(wednesday, !alarm.wed, alarm.reqCode + 3)
            alarmDatabase.getAlarmDao().updateWedRepeat(!alarm.wed, (alarm.reqCode))
        }
        thursday.setOnClickListener {
            setWeekColor(thursday, !alarm.thu, alarm.reqCode + 4)
            alarmDatabase.getAlarmDao().updateThuRepeat(!alarm.thu, (alarm.reqCode))
        }
        friday.setOnClickListener {
            setWeekColor(friday, !alarm.fri, alarm.reqCode + 5)
            alarmDatabase.getAlarmDao().updateFriRepeat(!alarm.fri, (alarm.reqCode))
        }
        saturday.setOnClickListener {
            setWeekColor(saturday, !alarm.sat, alarm.reqCode + 6)
            alarmDatabase.getAlarmDao().updateSatRepeat(!alarm.sat, (alarm.reqCode))
        }
        sunday.setOnClickListener {
            setWeekColor(sunday, !alarm.sun, alarm.reqCode + 7)
            alarmDatabase.getAlarmDao().updateSunRepeat(!alarm.sun, (alarm.reqCode))
        }

    }

    fun updateAlarmSetTime(cal: Calendar): String {
        var sdf: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return ("Alarm set on " + sdf.format(cal.timeInMillis))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAlarm() {
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var day: Int = 0

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
            day += 1
        }

//        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
//            day += 1
//        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
//            day += 2
//        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
//            day += 3
//        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
//            day += 4
//        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
//            day += 5
//        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
//            day += 6
//        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//            day += 7
//        }

        var i = Intent(this, AlarmBroadcastReceiver::class.java)
        i.putExtra("REQUEST_CODE", requestCode)
        var pendingIntent = PendingIntent.getBroadcast(this, requestCode, i, 0)



        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()

        setWeekRepeatingAlarm(monday, alarm.mon, (alarm.reqCode + 1))
        setWeekRepeatingAlarm(tuesday, alarm.tue, (alarm.reqCode + 2))
        setWeekRepeatingAlarm(wednesday, alarm.wed, (alarm.reqCode + 3))
        setWeekRepeatingAlarm(thursday, alarm.thu, (alarm.reqCode + 4))
        setWeekRepeatingAlarm(friday, alarm.fri, (alarm.reqCode + 5))
        setWeekRepeatingAlarm(saturday, alarm.sat, (alarm.reqCode + 6))
        setWeekRepeatingAlarm(sunday, alarm.sun, (alarm.reqCode + 7))
    }

    private fun setRepeatingAlarm(rq: Int) {
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var i = Intent(this, AlarmBroadcastReceiver::class.java)
        i.putExtra("REQUEST_CODE", rq)
        var pendingIntent = PendingIntent.getBroadcast(this, rq, i, 0)

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            (1000 * 60 * 60 * 24),
            pendingIntent
        )
    }

    private fun cancelRepeatingAlarm(rq: Int) {
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var i = Intent(this, AlarmBroadcastReceiver::class.java)
        i.putExtra("REQUEST_CODE", rq)
        var pendingIntent = PendingIntent.getBroadcast(this, rq, i, 0)
        alarmManager.cancel(pendingIntent)
    }

    //TODO : add delete alarm feature
    //TODO : add option to remove repeating alarm when making a day false
    //TODO : add setRepeatingAlarm feature

    fun setWeekColor(view: TextView, repeat: Boolean, rq: Int) {
        var color = if (repeat) ("#000000") else ("#bbbbbb")
        if ((repeat) && (rq != -1)) {
            setRepeatingAlarm(rq)
        } else if ((!repeat) && (rq != -1)) {
            cancelRepeatingAlarm(rq)
        }
        view.setTextColor(Color.parseColor(color))
    }

    fun setWeekRepeatingAlarm(view: TextView, repeat: Boolean, rq: Int) {
        if ((repeat) && (rq != -1)) {
            setRepeatingAlarm(rq)
        } else if ((!repeat) && (rq != -1)) {
            cancelRepeatingAlarm(rq)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 5) {
            i = data?.data!!.toString()
            alarm.ringtone = i
            alarmDatabase.getAlarmDao().insertAlarm(alarm)
        }
    }
}