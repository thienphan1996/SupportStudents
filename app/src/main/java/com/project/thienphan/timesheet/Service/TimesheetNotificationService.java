package com.project.thienphan.timesheet.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Model.NotificationItem;
import com.project.thienphan.timesheet.View.HomeActivity;
import com.project.thienphan.timesheet.View.MainActivity;
import com.project.thienphan.timesheet.View.SplashActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class TimesheetNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("From", "From: " + remoteMessage.getFrom());
        Log.d("Body", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d("Date", "Data: " + remoteMessage.getData().toString());
        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            Log.d("Notification body: ", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Map<String, String> data = remoteMessage.getData();
            String subject = data.get("subject");
            String title = data.get("title");
            String message = data.get("message");
            if (!subject.isEmpty() && !title.isEmpty() && !message.isEmpty()){
                Calendar calendar = Calendar.getInstance();
                TimesheetPreferences timesheetPreferences = new TimesheetPreferences(getApplicationContext());
                int notificationTotal = timesheetPreferences.get(getString(R.string.NOTIFICATION_TOTAL),Integer.class);
                if (notificationTotal >= 0){
                    notificationTotal++;
                    timesheetPreferences.put(getString(R.string.NOTIFICATION_TOTAL),notificationTotal);
                }
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                NotificationItem item = new NotificationItem(subject,title,message,sdfDate.format(calendar.getTime()) + "  " + sdfTime.format(calendar.getTime()),false);
                Gson gson = new Gson();
                String notifications = timesheetPreferences.get(getString(R.string.NOTIFICATION_STUDENT),String.class);
                if (notifications.isEmpty()){
                    ArrayList<NotificationItem> notificationList = new ArrayList<>();
                    notificationList.add(item);
                    String itemToString = gson.toJson(notificationList);
                    timesheetPreferences.put(getString(R.string.NOTIFICATION_STUDENT),itemToString);
                }
                else {
                    ArrayList<NotificationItem> notificationList = gson.fromJson(notifications,ArrayList.class);
                    if (notificationList != null){
                        notificationList.add(0,item);
                        String listToString = gson.toJson(notificationList);
                        timesheetPreferences.put(getString(R.string.NOTIFICATION_STUDENT),listToString);
                    }
                }
            }
            sendNotification(title,message);
        }

    }

    private void sendNotification(String title,String message) {
        String channelId = "studentsChanel";
        String channelName = "CTU students";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        Intent i = new Intent(this, SplashActivity.class);

        PendingIntent notificIntent = PendingIntent.getActivity(this,55, i ,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

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
                NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setTicker(title)
                .setSmallIcon(R.drawable.education_icon)
                .setLargeIcon(BitmapFactory.decodeResource( this.getResources(), R.mipmap.ic_launcher));

        mBuilder.setChannelId(channelId);
        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);

        mNotificationManager.notify(55, mBuilder.build());
    }
}
