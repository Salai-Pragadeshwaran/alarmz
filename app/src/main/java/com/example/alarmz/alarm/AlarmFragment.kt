package com.example.alarmz.alarm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmz.MainActivity
import com.example.alarmz.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AlarmFragment : Fragment() {

    companion object {

        fun newInstance() = AlarmFragment()

        @JvmStatic
        var alarms = ArrayList<AlarmInfo>()
        @JvmStatic
        lateinit var alarmRecycler2: RecyclerView
        @JvmStatic
        var requestCode : Int = 0
    }

    lateinit var alarmDatabase: AlarmDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root =  inflater.inflate(R.layout.fragment_alarm, container, false)

        alarmRecycler2 = root.findViewById(R.id.alarmRecycler)

        alarmDatabase = AlarmDatabase.get(MainActivity.appl)

        alarms.clear()
        alarms.addAll(alarmDatabase.getAlarmDao().getAllAlarms() as ArrayList<AlarmInfo>)
        var alarmAdapter = AlarmAdapter(alarms, root.context)
        alarmRecycler2.adapter = alarmAdapter
        if (alarms.size-1>=0) {
            requestCode = alarms[alarms.size-1].reqCode
        }

        var permission2 = ContextCompat.checkSelfPermission(
            root.context,
            Manifest.permission.READ_PHONE_STATE
        )

        if(permission2!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 103)
        }

        var fab: FloatingActionButton = root.findViewById(R.id.addAlarm)
        fab.setOnClickListener {
            requestCode += 8
            var ai = AlarmInfo(reqCode = requestCode)
            alarmDatabase.getAlarmDao().insertAlarm(ai)
            alarms.add(ai)
            var alarmAdapter = AlarmAdapter(alarms, root.context)
            alarmRecycler2.adapter = alarmAdapter
        }

        return root
    }

}