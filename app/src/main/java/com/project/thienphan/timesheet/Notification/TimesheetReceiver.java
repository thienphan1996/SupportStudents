package com.project.thienphan.timesheet.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.View.MainActivity;

public class TimesheetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context,intent);
    }

    public void createNotification(Context context, Intent intent)
    {
        String channelId = "studentsChanel";
        String channelName = "CTU students";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        String title = intent.getStringExtra(context.getString(R.string.NOTICATION_TITLE));
        String body = intent.getStringExtra(context.getString(R.string.NOTICATION_BODY));
        int id = intent.getIntExtra(context.getString(R.string.NOTICATION_ID),0);

        Intent i = new Intent(context, MainActivity.class);

        PendingIntent notificIntent = PendingIntent.getActivity(context,id, i ,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new
                NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setTicker(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource( context.getResources(), R.mipmap.ic_launcher));

        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        mNotificationManager.notify(id, mBuilder.build());
    }
}

