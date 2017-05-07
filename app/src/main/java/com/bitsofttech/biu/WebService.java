package com.bitsofttech.biu;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shahed on 3/10/2017.
 */

public class WebService extends AsyncTask<String, String,List<NewsModel>>{

    Context context;
    private ProgressDialog Dialog;

    DatabaseHelper DB;

    public WebService(Context context) {
        this.context = context;
        Dialog = new ProgressDialog(context);
        DB = new DatabaseHelper(context);
    }


        @Override
        protected List<NewsModel> doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line="";

                while ((line=reader.readLine()) !=null){

                    buffer.append(line);
                }


                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("news");


                List<NewsModel> postsModelList = new ArrayList<>();


                for(int i=0; i<parentArray.length();i++){
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    Log.d("title",finalObject.getString("post_title"));

                    DB.insertPost(
                            finalObject.getString(DB.POSTID),
                            finalObject.getString(DB.POSTTITLE),
                            finalObject.getString(DB.POSTCONTENT),
                            finalObject.getString(DB.POSTTYPE),
                            finalObject.getString(DB.POSTURL),
                            finalObject.getString(DB.POSTCATEGORY),
                            finalObject.getString(DB.POSTDATE));
                }

                return postsModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();
                try {
                    if(reader !=null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DB.openDB();
            Dialog.setMessage("Getting data from web server....");
            Dialog.show();
        }

        @Override
        protected void onPostExecute(List<NewsModel> result) {
            super.onPostExecute(result);
            Dialog.dismiss();
            DB.closeDB();

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Dialog.setProgress(Integer.parseInt(String.valueOf(values)));
        }

}
