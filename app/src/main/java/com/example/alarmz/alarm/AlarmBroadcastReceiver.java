package com.example.alarmz.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "check check", Toast.LENGTH_LONG).show();
        Intent i = new Intent(context, AlarmFiredActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pi = PendingIntent.getActivity(context, 555, i, PendingIntent.FLAG_UPDATE_CURRENT);
        context.startActivity(i);

//        Intent launch_intent = new Intent("android.intent.action.MAIN");
//        launch_intent.setComponent(new ComponentName(context.getPackageName(), SetAlarmActivity.class.getName()));
//        launch_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        launch_intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        context.startActivity(launch_intent);
        //context.startActivity(i);
    }
}
