package com.project.thienphan.teacher.Service;

import android.app.Activity;
import android.os.AsyncTask;
import com.project.thienphan.timesheet.Support.InfoDialog;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushNotificationService {

    public static void Send(final String subject, final String title, final String message, final String topic, final Activity activity){
        new AsyncTask<Void, Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    String apiKey = "AAAAbX_1xm0:APA91bHDv4P-0Oit0rkw-yCjFs04ltGmNffsy--C57mS90WGBlqpno0dw6zTsS1ZGzbEN5U4-NUWPlhNgpWjffCgU2MKPpbyM5WFW39mNXY4R7MPkxqMyF9Hgg3JLyfrriqwkqsiVTezixIzTRAnL2hA-1Xk4It17w";
                    String content = "{\"to\": \"/topics/"+ topic + "\", \"data\": {\"subject\": \""+ subject +"\"}, {\"title\": \""+ title +"\"}, {\"message\": \""+ message +"\"}}";
                    String urlString = "https://fcm.googleapis.com/fcm/send";
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setInstanceFollowRedirects(false);
                    urlConnection.setUseCaches(false);
                    urlConnection.setRequestMethod("PUT");
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                    urlConnection.setRequestProperty("Authorization", "key=" + apiKey);
                    int responseCode = urlConnection.getResponseCode();

                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    wr.writeBytes(content);
                    wr.flush();
                    wr.close();
                    if (responseCode == 200){
                        return true;
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                try {
                    if (result){
                        InfoDialog.ShowInfoDiaLog(activity,"Thông báo","Gửi thành công!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
