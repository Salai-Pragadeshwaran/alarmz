package com.example.alarmz.alarm

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(version = 1, entities = [AlarmInfo::class])
abstract class AlarmDatabase : RoomDatabase(){
    companion object{
        fun get(application: Application): AlarmDatabase{
            return Room.databaseBuilder(application, AlarmDatabase::class.java, "alarm data").allowMainThreadQueries().build()
        }
    }

    abstract fun getAlarmDao(): AlarmDao
}