package com.bitsofttech.biu;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Shahed on 4/5/2017.
 */

public class ReadAssetFile {
    Context context;
    //InputStream reader = null;

    public ReadAssetFile(Context context) {
        this.context = context;
    }

    public String read_file(String fileName){
        StringBuffer buf = new StringBuffer();

        try {
            /*reader = context.getAssets().open(fileName);

            byte[] buffer = new byte[1024];
            String line;
            String content = null;

            while ((line = reader.re) > 0) {
                content =+ buffer;
            }*/

            String str="";

            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            if (is!=null) {
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n" );
                }
            }
            is.close();

            //Toast.makeText(context,
              //      buf.toString(), Toast.LENGTH_LONG).show();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }
}
