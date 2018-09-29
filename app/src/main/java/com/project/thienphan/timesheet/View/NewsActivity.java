package com.project.thienphan.timesheet.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.NewsAdapter;
import com.project.thienphan.timesheet.Model.TagContent;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ArrayList<TagContent> lstNotify;
    NewsAdapter notifyAdapter;
    RecyclerView rcvNews;
    TimesheetProgressDialog dialog;

    ImageButton imgPrevious,imgNext;
    String targetUrl = "";
    int paramNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addEvents() {
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandlingNextPage();
            }
        });
        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandlingPreviousPage();
            }
        });
    }

    private void HandlingPreviousPage() {
        paramNumber -= 15;
        getWebsite(paramNumber);
        imgNext.setEnabled(true);
        imgNext.setImageResource(R.drawable.ic_navigate_next_black_24dp);
        imgPrevious.setEnabled(true);
        imgPrevious.setImageResource(R.drawable.ic_navigate_before_black_24dp);
        if (paramNumber == 0){
            imgPrevious.setEnabled(false);
            imgPrevious.setImageResource(R.drawable.ic_navigate_before_black_24dp_disable);
        }
    }

    private void HandlingNextPage() {
        paramNumber += 15;
        getWebsite(paramNumber);
        imgNext.setEnabled(true);
        imgNext.setImageResource(R.drawable.ic_navigate_next_black_24dp);
        imgPrevious.setEnabled(true);
        imgPrevious.setImageResource(R.drawable.ic_navigate_before_black_24dp);
        if (paramNumber >= 75){
            imgNext.setEnabled(false);
            imgNext.setImageResource(R.drawable.ic_navigate_next_black_24dp_disable);
        }
    }


    private void addControls() {
        imgNext = findViewById(R.id.img_next_news);
        imgPrevious = findViewById(R.id.img_previous_news);
        paramNumber = 0;
        lstNotify = new ArrayList<>();
        rcvNews = findViewById(R.id.rcv_news);
        notifyAdapter = new NewsAdapter(lstNotify, getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewSiteDetails(i);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvNews.setLayoutManager(layoutManager);
        rcvNews.setAdapter(notifyAdapter);
        dialog = new TimesheetProgressDialog();
        getWebsite(0);
        imgPrevious.setEnabled(false);
    }

    private void getWebsite(int param) {
        final String newsUrl = getString(R.string.news_url) + param;
        dialog.show(getSupportFragmentManager(),"");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lstNotify.clear();
                    Document doc = Jsoup.connect(newsUrl).get();
                    Elements links = doc.select("a[href]");
                    for (Element link : links) {
                        String keyLink = link.attr("href");
                        String body = link.text();
                        if (!keyLink.isEmpty() && keyLink.length() > 10 && !body.isEmpty() && keyLink.substring(0,11).equals(getString(R.string.html_notify))){
                            TagContent content = new TagContent(keyLink,body);
                            if (!lstNotify.contains(content) && lstNotify.size() <= 15){
                                lstNotify.add(new TagContent(keyLink,body));
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.d("Error: " , e.toString());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        notifyAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void ViewSiteDetails(int index){
        final String ctuUrl = getString(R.string.ctu_url);
        final String urlFromIndex = ctuUrl + lstNotify.get(index).getLink();
        dialog.show(getSupportFragmentManager(),"");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(urlFromIndex).get();
                    Elements links = doc.select("a[href]");

                    for (Element link : links) {
                        String keyLink = link.attr("href");
                        String body = link.text();
                        try {
                            if (keyLink.length() > 12 && body.trim().substring(body.length() - 12,body.length()).equals("Xem chi tiết") && keyLink.substring(keyLink.length()-4,keyLink.length()).equals(".pdf")){
                                targetUrl = "https://drive.google.com/viewer?url=" + ctuUrl + keyLink;
                                break;
                            }
                            else if (body.length() > 12 && body.trim().substring(body.length() - 12,body.length()).equals("Xem chi tiết")){
                                if (keyLink.length() > 5){
                                    if (keyLink.substring(0,4).equals("http")){
                                        targetUrl = keyLink;
                                        break;
                                    }
                                    else {
                                        targetUrl = ctuUrl + keyLink;
                                        break;
                                    }
                                }
                            }
                            else {
                                targetUrl = urlFromIndex;
                            }
                        }
                        catch (Exception e){

                        }
                    }
                } catch (IOException e) {
                    Log.d("Error: " , e.toString());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Intent intent = new Intent(NewsActivity.this,TimesheetDetails.class);
                        intent.putExtra(getString(R.string.TS_DETAILS),targetUrl);
                        intent.putExtra(getString(R.string.TS_DETAILS_FROM_NEWS),1);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }
}
