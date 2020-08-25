package com.example.alarmz.alarm

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmz.MainActivity
import com.example.alarmz.R
import kotlinx.android.synthetic.main.activity_set_alarm.view.*
import kotlinx.android.synthetic.main.alarm_item.view.*
import kotlinx.android.synthetic.main.fragment_stop_watch.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AlarmAdapter(val alarms: ArrayList<AlarmInfo>, private val mcontext: Context):
    RecyclerView.Adapter<AlarmAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var time: TextView
        internal var container: LinearLayout

        init{
            time = itemView.alarmTime
            container = itemView.alarmItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarms[position].hour)
        calendar.set(Calendar.MINUTE, alarms[position].minutes)
        var sdf: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        holder.time.text = sdf.format(calendar.timeInMillis)
        holder.container.setOnClickListener {
            var i = Intent(mcontext, SetAlarmActivity::class.java)
            i.putExtra("HOUR", alarms[position].hour)
            i.putExtra("REQUEST_CODE", alarms[position].reqCode)
            i.putExtra("MINUTE", alarms[position].minutes)
            mcontext.startActivity(i)
        }
    }

}