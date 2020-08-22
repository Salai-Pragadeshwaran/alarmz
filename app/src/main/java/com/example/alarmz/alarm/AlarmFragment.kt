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
import com.example.alarmz.R
import kotlinx.android.synthetic.main.fragment_alarm.*

class AlarmFragment : Fragment() {

    companion object {

        fun newInstance() = AlarmFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root =  inflater.inflate(R.layout.fragment_alarm, container, false)


        var permission2 = ContextCompat.checkSelfPermission(
            root.context,
            Manifest.permission.READ_PHONE_STATE
        )

        if(permission2!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 103)
        }

        var intentSetAlarm: Button = root.findViewById(R.id.intentSetAlarm)
        intentSetAlarm.setOnClickListener {
            var i = Intent(root.context, SetAlarmActivity::class.java)
            startActivity(i)
        }

        return root
    }

}