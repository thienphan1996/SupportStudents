package com.project.thienphan.timesheet.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.project.thienphan.supportstudent.R;

public class TimesheetDetails extends AppCompatActivity {

    WebView webview;
    ProgressDialog pDialog;
    ImageButton imgBack;
    String subCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesheet_details);
        
        addControls();
        addEvents();
    }

    private void addEvents() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        Intent intent = getIntent();

        if (intent != null){
            subCode = intent.getStringExtra(getString(R.string.TS_DETAILS));
        }
        Log.d("Sub code: ",""  + subCode);
        imgBack = findViewById(R.id.img_tsdetails_back);
        webview = (WebView) findViewById(R.id.web_timesheet_detail);
        webview.getSettings().setJavaScriptEnabled(true);

        pDialog = new ProgressDialog(TimesheetDetails.this);
        pDialog.setTitle("PDF");
        pDialog.setMessage("Đang tải...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webview.loadUrl("https://drive.google.com/viewer?url=http://www.cit.ctu.edu.vn/decuong/" + subCode +".pdf");
    }
}
