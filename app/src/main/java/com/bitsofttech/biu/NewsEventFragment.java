package com.bitsofttech.biu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class NewsEventFragment extends Fragment {

    View view;
    ListView newsEventListView;
    AppPreference appPreference;
    List<NewsModel> newsModelList;
    DatabaseHelper DB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_news_event_fragment,container,false);
        this.view=view;

        init();
       // syncNews();
        loadDataToListView();
        return view ;
    }

    private void init() {
        DB = new DatabaseHelper(getContext());
        appPreference = new AppPreference(getContext());
        newsEventListView = (ListView) view.findViewById(R.id.newEventListView);
    }



    private  void loadDataToListView(){

        DB.openDB();

        Cursor post = DB.getAllPosts();
        newsModelList = new ArrayList<>();

        for(post.moveToFirst();!post.isAfterLast();post.moveToNext()) {

            NewsModel newsModel = new NewsModel();

            newsModel.setNewsServerPostID(post.getString(post.getColumnIndex(DB.ID)));
            newsModel.setNewsTitle(post.getString(post.getColumnIndex(DB.POSTTITLE)));
            newsModel.setNewsDetail(post.getString(post.getColumnIndex(DB.POSTCONTENT)));
            newsModel.setNewsDate(post.getString(post.getColumnIndex(DB.POSTDATE)));
            newsModel.setNewsCategory(post.getString(post.getColumnIndex(DB.POSTCATEGORY)));



            Log.d("title",post.getString(post.getColumnIndex(DB.POSTTITLE)));
            newsModelList.add(newsModel);

        }

        PostAdapter postAdapter = new PostAdapter(getContext(),R.layout.news_list_view_template,newsModelList);
        newsEventListView.setAdapter(postAdapter);


        newsEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String htmlData= Html.fromHtml(newsModelList.get(position).getNewsDetail()).toString();
                htmlData= Html.fromHtml(htmlData).toString();

                appPreference.set_pref(appPreference.PAGETITLE,newsModelList.get(position).getNewsTitle());
                appPreference.set_pref(appPreference.PAGECONTENT,htmlData);
                appPreference.set_pref(appPreference.NEWSCATEGORY,newsModelList.get(position).getNewsCategory());
                appPreference.set_pref(appPreference.NEWSDATE,newsModelList.get(position).getNewsDate());

                Intent intent = new Intent(getContext(),NewsDetail.class);
                startActivity(intent);
            }
        });
    }


    public  class PostAdapter extends ArrayAdapter {

        private List<NewsModel> newsModelList;
        private int resources;
        private LayoutInflater inflater;
        TextView postTitle,postDate,postCat;

        public PostAdapter(Context context, int resource, List<NewsModel> objects) {
            super(context, resource, objects);

            newsModelList=objects;
            this.resources=resource;

            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView==null){

                convertView=inflater.inflate(resources,null);
            }

            //Initialization
            postTitle=(TextView) convertView.findViewById(R.id.newsTitleTextView);
            postDate=(TextView)convertView.findViewById(R.id.newsDateTextView);
            postCat=(TextView)convertView.findViewById(R.id.categoryTextView);

           // final String postDBid = String.valueOf(newsModelList.get(position).getNewsServerPostID());

            //Set data
            postTitle.setText(newsModelList.get(position).getNewsTitle());
            postDate.setText(newsModelList.get(position).getNewsDate()+", ");
            postCat.setText(newsModelList.get(position).getNewsCategory());

            return convertView;
        }




    }
}
