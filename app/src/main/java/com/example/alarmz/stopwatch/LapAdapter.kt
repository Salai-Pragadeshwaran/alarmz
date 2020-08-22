package com.example.alarmz.stopwatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmz.R
import kotlinx.android.synthetic.main.lap_item.view.*
import java.lang.String
import java.util.*
import java.util.concurrent.TimeUnit

class LapAdapter(var laps: ArrayList<Lap>) : RecyclerView.Adapter<LapAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var lapNoText: TextView
        internal var timeElapsedText: TextView
        internal var differenceText: TextView

        init {
            lapNoText = itemView.lapNum
            timeElapsedText = itemView.elapsedTime
            differenceText = itemView.timeDifference
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lap_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return laps.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lapNoText.text = laps[position].lapNo.toString()
        holder.differenceText.text = returnFormattedTimeString(laps[position].timeDifference)
        holder.timeElapsedText.text = returnFormattedTimeString(laps[position].elapsedTime)

        if (position == (laps.size - 1)) {
            setFadeAnimation(holder.lapNoText)
            setFadeAnimation(holder.timeElapsedText)
            setFadeAnimation(holder.differenceText)
        }

//        var cal : Calendar = Calendar.getInstance(Locale.ENGLISH)
//        var sdf: SimpleDateFormat = SimpleDateFormat("HH:mm:ss:SS", Locale.getDefault())
//
//
//
//        cal.timeInMillis= laps[position].elapsedTime
//        holder.timeElapsedText.text = sdf.format(cal.timeInMillis)

    }

    private fun returnFormattedTimeString(l: Long): kotlin.String {
        var hr = TimeUnit.MILLISECONDS.toHours(l);
        var min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
        var sec = TimeUnit.MILLISECONDS.toSeconds(
            l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min)
        );
        var ms = TimeUnit.MILLISECONDS.toMillis(
            l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(
                sec
            )
        );
        return String.format("%02d:%02d.%03d", min, sec, ms)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.setDuration(1000)
        view.startAnimation(anim)
    }

}