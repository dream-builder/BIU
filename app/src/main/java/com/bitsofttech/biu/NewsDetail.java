package com.bitsofttech.biu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class NewsDetail extends AppCompatActivity {
    TextView titleTextView,newsDateTV,newsCategoryTV;
    WebView newsWebView;
    AppPreference appPreference;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        init();
        loadData();

        loadAds();
    }

    private void loadAds() {

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void init() {
        appPreference = new AppPreference(this);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        newsWebView = (WebView) findViewById(R.id.newsWebView);
        newsCategoryTV = (TextView) findViewById(R.id.newsCategoryTextView);
        newsDateTV = (TextView) findViewById(R.id.newsDateTextView);
    }

    private void loadData(){

        titleTextView.setText(appPreference.get_pref(appPreference.PAGETITLE));
        newsCategoryTV.setText(appPreference.get_pref(appPreference.NEWSCATEGORY));
        newsDateTV.setText(appPreference.get_pref(appPreference.NEWSDATE));

        String data= appPreference.get_pref(appPreference.PAGECONTENT);

        newsWebView.loadData(data,"text/html; charset=utf-8", "UTF-8");
    }
}
