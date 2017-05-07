package com.bitsofttech.biu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by Shahed on 2/22/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME="biudb.db";
    private static final int VERSION=1;
    private static final String POSTTABLE ="posts";
    public static final String ID="_id";
    public static final String POSTID="post_id";
    public static final String POSTTITLE="post_title";
    public static final String POSTCATEGORY="post_category";
    public static final String POSTAUTHOR="post_author";
    public static final String POSTTYPE="post_type";
    public static final String POSTURL="post_url";
    public static final String POSTCONTENT="post_content";
    public static final String POSTDATE="post_date";


    private SQLiteDatabase ijDB;
    public Context context;

    public DatabaseHelper(Context context) {
        super(context, DBNAME,null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query ="CREATE TABLE IF NOT EXISTS "+ POSTTABLE +"(";
            query   +=  ID +" INTEGER PRIMARY KEY AUTOINCREMENT,";
            query   +=  POSTID+" TEXT NOT NULL,";
            query   +=  POSTTITLE+" TEXT NOT NULL,";
            query   +=  POSTAUTHOR+" TEXT NOT NULL,";
            query   +=  POSTCONTENT+" TEXT NOT NULL,";
            query   +=  POSTCATEGORY+" TEXT NOT NULL,";
            query   +=  POSTTYPE+" TEXT NOT NULL,";
            query   +=  POSTURL+" TEXT NOT NULL,";
            query   +=  POSTDATE+" TEXT NOT NULL";
            query   +=  ")";

        //db.execSQL(query);

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public  void openDB(){
        ijDB = getWritableDatabase();
    }

    public void closeDB(){
        if(ijDB != null && ijDB.isOpen())
            ijDB.close();
    }


    public Cursor getAllPosts(){

        String query ="SELECT * FROM "+ POSTTABLE;
        return ijDB.rawQuery(query,null);
    }

    public void deleteAllPost(){
        String query ="DELETE FROM "+ POSTTABLE;
        ijDB.execSQL(query);
    }

    public long insertPost(String post_id,
                            String post_title,
                            String post_content,
                            String post_type,
                            String post_url,
                            String post_category,
                            String post_date
                            )
    {

        ContentValues values = new ContentValues();


        if(!isPostExists(post_id)) {

            values.put(POSTID, post_id);
            values.put(POSTTITLE, post_title);
            values.put(POSTCONTENT, post_content);
            values.put(POSTTYPE, post_type);
            values.put(POSTURL, post_url);
            values.put(POSTAUTHOR, "Software");
            values.put(POSTCATEGORY, post_category);
            values.put(POSTDATE, post_date);

           return ijDB.insert(POSTTABLE,null,values);
        }
        //Log.d("sql","insert");

        return -1;
    }

    public boolean isPostExists(String ID){

        String query ="SELECT "+POSTID+" FROM "+ POSTTABLE + " WHERE " + POSTID+"='"+ID+"'";

        Cursor cursor = ijDB.rawQuery(query,null);

        Log.d("query",query+ String.valueOf(cursor.getCount()));

        if(cursor.getCount()>0)
            return true;


        return false;
    }

}
