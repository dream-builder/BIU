package com.bitsofttech.biu;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Shahed on 3/8/2017.
 */

public class AppPreference {
    public static final String PAGETITLE="page_title";
    public static final String PAGEIMAGE="page_image";
    public static final String PAGECONTENT ="page_content";
    public static final String NEWSDATE ="news_date";
    public static final String NEWSCATEGORY ="news_category";
    public static final String CONTACTSYNC ="contact_sync";





    Context context;

    private SharedPreferences pref;

    public AppPreference(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("APP_PREF",context.MODE_PRIVATE);
    }


    public void  set_pref(String key, String val){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,val);
        editor.commit();
    }

    public String get_pref(String key){
        return pref.getString(key,"0");
    }




}
