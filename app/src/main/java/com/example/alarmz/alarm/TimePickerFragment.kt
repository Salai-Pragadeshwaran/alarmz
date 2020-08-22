package com.example.alarmz.alarm

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.*

class TimePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var c = Calendar.getInstance()
        var hour = c.get(Calendar.HOUR_OF_DAY)
        var minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, activity as TimePickerDialog.OnTimeSetListener, hour, minute, false)
    }
}