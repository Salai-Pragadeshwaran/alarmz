package com.example.alarmz.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarmz!", Toast.LENGTH_LONG).show();
        Boolean shouldRing = false;
        Calendar calendar = Calendar.getInstance();
        int day = (intent.getIntExtra("REQUEST_CODE", 0))%8;
        if ((day==1)&&(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)){
            shouldRing = true;
        }else if ((day==2)&&(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)){
            shouldRing = true;
        }else if ((day==3)&&(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)){
            shouldRing = true;
        }else if ((day==4)&&(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)){
            shouldRing = true;
        }else if ((day==5)&&(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)){
            shouldRing = true;
        }else if ((day==6)&&(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)){
            shouldRing = true;
        }else if ((day==7)&&(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
            shouldRing = true;
        }else if(day==0){
            shouldRing = true;
        }
        if (shouldRing) {
            Intent i = new Intent(context, AlarmFiredActivity.class);
            i.putExtra("REQUEST_CODE", intent.getIntExtra("REQUEST_CODE", -1));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //PendingIntent pi = PendingIntent.getActivity(context, 555, i, PendingIntent.FLAG_UPDATE_CURRENT);
            context.startActivity(i);
        }

//        Intent launch_intent = new Intent("android.intent.action.MAIN");
//        launch_intent.setComponent(new ComponentName(context.getPackageName(), SetAlarmActivity.class.getName()));
//        launch_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        launch_intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        context.startActivity(launch_intent);
        //context.startActivity(i);
    }
}
