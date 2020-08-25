package com.example.alarmz.alarm

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alarm: AlarmInfo)

    @Query("select * from AlarmInfo")
    fun getAllAlarms():List<AlarmInfo>

    @Query("select * from AlarmInfo WHERE reqCode = :rq")
    fun getAlarm(rq: Int):AlarmInfo

    @Query("UPDATE AlarmInfo SET hour = :hr, minutes = :min WHERE reqCode = :rq")
    fun updateAlarmTime(rq: Int, hr: Int, min: Int)

    @Query("UPDATE AlarmInfo SET ringtone = :uri WHERE reqCode = :rq")
    fun updateRingtone(rq: Int, uri: String)

    @Query("UPDATE AlarmInfo SET mon = :repeat WHERE reqCode = :rq")
    fun updateMonRepeat(repeat: Boolean, rq: Int)

    @Query("UPDATE AlarmInfo SET tue = :repeat WHERE reqCode = :rq")
    fun updateTueRepeat(repeat: Boolean, rq: Int)

    @Query("UPDATE AlarmInfo SET wed = :repeat WHERE reqCode = :rq")
    fun updateWedRepeat(repeat: Boolean, rq: Int)

    @Query("UPDATE AlarmInfo SET thu = :repeat WHERE reqCode = :rq")
    fun updateThuRepeat(repeat: Boolean, rq: Int)

    @Query("UPDATE AlarmInfo SET fri = :repeat WHERE reqCode = :rq")
    fun updateFriRepeat(repeat: Boolean, rq: Int)

    @Query("UPDATE AlarmInfo SET sat = :repeat WHERE reqCode = :rq")
    fun updateSatRepeat(repeat: Boolean, rq: Int)

    @Query("UPDATE AlarmInfo SET sun = :repeat WHERE reqCode = :rq")
    fun updateSunRepeat(repeat: Boolean, rq: Int)
}