package com.bitsofttech.biu;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class biu_info extends Fragment {

    View view;
    TextView detailTextView,titleTextView;
    ReadAssetFile readAssetFile;
    WebView webView;
    AppPreference appPreference;
    ImageView pageThumb;
    ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_biu_info,container,false);

        this.view=view;
        init();
        loadData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void init() {
        appPreference = new AppPreference(getContext());

        scrollView = (ScrollView) view.findViewById(R.id.activity_biu_info);
        detailTextView = (TextView) view.findViewById(R.id.detailTextView);
        titleTextView = (TextView) view.findViewById(R.id.pageTitleTextView);
        pageThumb =(ImageView) view.findViewById(R.id.thumbImageView);

        webView = (WebView) view.findViewById(R.id.webView);

        readAssetFile = new ReadAssetFile(getContext());

        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(0,50);
            }
        });

    }

    private void loadData(){

        //scrollView.smoothScrollTo(0,0);
        //scrollView.pageScroll(View.FOCUS_UP);
        titleTextView.setText(appPreference.get_pref(appPreference.PAGETITLE));
        String text=appPreference.get_pref(appPreference.PAGECONTENT);
        int imageID = Integer.parseInt(appPreference.get_pref(appPreference.PAGEIMAGE));

        if(imageID>0) {
            pageThumb.setVisibility(View.VISIBLE);
            pageThumb.setImageResource(imageID);
        }
        else
            pageThumb.setVisibility(View.GONE);

        //String text=readAssetFile.read_file("about_biu.txt");;
        //detailTextView.setText(Html.fromHtml(text));

        webView.loadData(text,"text/html; charset=utf-8", "UTF-8");

        Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        }, 1000); // 250 ms delay

    }




}
