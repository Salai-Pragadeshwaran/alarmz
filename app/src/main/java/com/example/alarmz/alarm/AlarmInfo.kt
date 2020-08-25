package com.example.alarmz.alarm

import android.net.Uri
import android.provider.Settings
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class AlarmInfo(
    var hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    var minutes: Int = Calendar.getInstance().get(Calendar.MINUTE),
    var mon: Boolean = false,
    var tue: Boolean = false,
    var wed: Boolean = false,
    var thu: Boolean = false,
    var fri: Boolean = false,
    var sat: Boolean = false,
    var sun: Boolean = false,
    var ringtone: String = Settings.System.DEFAULT_RINGTONE_URI.toString(),
    @PrimaryKey var reqCode: Int = 0
){

}