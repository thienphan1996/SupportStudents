package com.project.thienphan.timesheet.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.View.MainActivity;
import com.project.thienphan.timesheet.View.SplashActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class TimesheetReceiver extends BroadcastReceiver {

    TimesheetPreferences timesheetPreferences;
    Gson gson;
    @Override
    public void onReceive(Context context, Intent intent) {
        timesheetPreferences = new TimesheetPreferences(context);
        gson = new Gson();
        String mytimesheet = timesheetPreferences.get(context.getString(R.string.MY_TIMESHEET),String.class);
        if (!mytimesheet.isEmpty()){
            String ts = timesheetPreferences.get(context.getString(R.string.MY_TIMESHEET),String.class);
            Type myType = new TypeToken<ArrayList<TimesheetItem>>(){}.getType();
            ArrayList<TimesheetItem> tsList = (ArrayList<TimesheetItem>) gson.fromJson(ts,myType);
            if (tsList != null){
                Calendar calendar = Calendar.getInstance();
                int today = calendar.get(Calendar.DAY_OF_WEEK);
                for (TimesheetItem item : tsList){
                    if (item.getDayofWeek() == today){
                        createNotification(context,intent,item.getSubjectName(),"Hôm nay bạn có 1 buổi học môn " + item.getSubjectName() + ", tiết " + item.getSubjectTime() + ", phòng" + item.getSubjectLocation());
                        break;
                    }
                }
            }
        }
    }

    public void createNotification(Context context, Intent intent, String title, String message)
    {
        String channelId = "studentsChanel";
        String channelName = "CTU students";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        Intent i = new Intent(context, SplashActivity.class);

        PendingIntent notificIntent = PendingIntent.getActivity(context,55, i ,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder mBuilder = new
                NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setTicker(title)
                .setSmallIcon(R.drawable.education_icon)
                .setLargeIcon(BitmapFactory.decodeResource( context.getResources(), R.mipmap.ic_launcher));
        mBuilder.setChannelId(channelId);
        mBuilder.setContentIntent(notificIntent);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);

        mNotificationManager.notify(55, mBuilder.build());
    }
}

