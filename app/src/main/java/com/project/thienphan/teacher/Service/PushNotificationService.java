package com.project.thienphan.teacher.Service;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Model.NotificationItem;
import com.project.thienphan.timesheet.Support.InfoDialog;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PushNotificationService {

    TimesheetProgressDialog dialog;
    FragmentManager fmManager;
    TimesheetPreferences timesheetPreferences;
    Activity activity;

    public PushNotificationService(FragmentManager fmManager,Activity activity) {
        this.fmManager = fmManager;
        dialog = new TimesheetProgressDialog();
        this.activity = activity;
        timesheetPreferences = new TimesheetPreferences(activity.getApplicationContext());
    }

    private String apiKey = "AAAAbX_1xm0:APA91bHDv4P-0Oit0rkw-yCjFs04ltGmNffsy--C57mS90WGBlqpno0dw6zTsS1ZGzbEN5U4-NUWPlhNgpWjffCgU2MKPpbyM5WFW39mNXY4R7MPkxqMyF9Hgg3JLyfrriqwkqsiVTezixIzTRAnL2hA-1Xk4It17w";

    public void Send(final String subjectName,final String subject,final String title, final String message, final String topic){
        dialog.show(fmManager,"dialog");
        new AsyncTask<Void, String, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                try {
                    String body = "{\"to\": \"/topics/"+ topic + "\", \"data\": {\"subject\": \""+ subject +"\", \"title\": \""+ subjectName +" - "+ title +"\", \"message\": \""+ message +"\"}}";
                    int result = postToFCM(body);
                    return result;
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer result) {
                dialog.dismiss();
                try {
                    int responeCode = result;
                    if (responeCode == 200){
                        AddNotificationToSharePreferences(subject,subjectName +" - "+ title,message);
                        InfoDialog.ShowInfoDiaLog(activity,"Thông báo","Gửi thông báo thành công.");
                    }
                    else {
                        InfoDialog.ShowInfoDiaLog(activity,"Lỗi","Gửi thông báo thất bại.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    InfoDialog.ShowInfoDiaLog(activity,"Lỗi","Gửi thông báo thất bại.");
                }
            }
        }.execute();
    }

    private void AddNotificationToSharePreferences(String subjectCode, String title, String message) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        NotificationItem item = new NotificationItem(subjectCode,title,message,sdfDate.format(calendar.getTime()) + "  " + sdfTime.format(calendar.getTime()),true);
        Gson gson = new Gson();
        String notifications = timesheetPreferences.get("NOTIFICATION_TEACHER",String.class);
        if (notifications.isEmpty()){
            ArrayList<NotificationItem> list = new ArrayList<>();
            list.add(item);
            String dataToString = gson.toJson(list);
            timesheetPreferences.put("NOTIFICATION_TEACHER",dataToString);
        }
        else {
            Type myType = new TypeToken<ArrayList<NotificationItem>>(){}.getType();
            ArrayList<NotificationItem> data = (ArrayList<NotificationItem>) gson.fromJson(notifications, myType);
            if (data != null){
                data.add(0,item);
            }
            String dataToString = gson.toJson(data);
            timesheetPreferences.put("NOTIFICATION_TEACHER",dataToString);
        }
    }

    private int postToFCM(String bodyString) throws IOException {

        OkHttpClient mClient = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("Authorization", "key=" + apiKey)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.code();
    }
}
