package com.project.thienphan.timesheet.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

public class TimesheetDetails extends AppCompatActivity {

    WebView webview;
    TimesheetProgressDialog pDialog;
    ImageButton imgBack;
    String url;
    int fromNews = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesheet_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgBack = findViewById(R.id.img_tsdetails_back);

        addControls();
        if (fromNews == -1){
            getSupportActionBar().hide();
            imgBack.setVisibility(View.VISIBLE);
        }
        else {
            imgBack.setVisibility(View.GONE);
        }
        addEvents();
    }

    private void addEvents() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show(getSupportFragmentManager(),"");
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
            url = intent.getStringExtra(getString(R.string.TS_DETAILS));
            fromNews = intent.getIntExtra(getString(R.string.TS_DETAILS_FROM_NEWS),-1);
        }
        Log.d("Sub code: ",""  + url);
        webview = (WebView) findViewById(R.id.web_timesheet_detail);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webview.getSettings().setSupportMultipleWindows(false);
        webview.getSettings().setSupportZoom(false);

        pDialog = new TimesheetProgressDialog();
        webview.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
