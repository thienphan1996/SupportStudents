package com.project.thienphan.timesheet.View;

import android.annotation.SuppressLint;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.project.thienphan.supportstudent.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        //GetNews();
        new MyDownloadTask().execute();
    }

    private void GetNews() {
        try {
            GetExample example = new GetExample();
            String response = example.run(getString(R.string.url_login_ctu));
            Log.d("Ket qua",response);
        } catch (Exception e) {
            Log.d("Loi o day: ",e.getMessage());
        }
    }

    public class GetExample {
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }
    private void addControls() {

    }

    class MyDownloadTask extends AsyncTask<Void,Void,Void>
    {


        protected void onPreExecute() {
            //display progress dialog.
            Log.d("Thanh cong","");
        }
        protected Void doInBackground(Void... params) {
            URL urlLoc = null;
            try {
                urlLoc = new URL("https://htql.ctu.edu.vn/htql/login.php");
                URLConnection conexion = urlLoc.openConnection();
                conexion.setConnectTimeout(4000);
                conexion.setReadTimeout(1000);
                conexion.connect();
                InputStream input = new BufferedInputStream(urlLoc
                        .openStream());

                StringBuffer responseBuffer = new StringBuffer();
                byte[] byteArray = new byte[1024];
                while (input.read(byteArray) != -1)
                {
                    String res = new String(byteArray, "UTF-8");
                    responseBuffer.append(res);
                    byteArray = new byte[1024];
                }
                String response = responseBuffer.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onPostExecute(Void result) {
            // dismiss progress dialog and update ui
            Log.d("Thanh cong","");
        }
    }
}
