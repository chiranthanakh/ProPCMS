package com.proteam.propcms.Activity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class Storageutils {

    public static String getrealpath(Uri uri, Context context){

        String realpath="";

        if(uri.getScheme().equalsIgnoreCase("content")){
            String[] projection={"_data"};
            Cursor cursor=context.getContentResolver().query(uri,null,null,null,null);

            if(cursor!=null){

                cursor.moveToFirst();
                int idcolumn=cursor.getColumnIndexOrThrow("_data");
                cursor.moveToFirst();
                realpath=cursor.getString(idcolumn);
                cursor.close();
            }
        }else if(uri.getScheme().equalsIgnoreCase("file")){
            realpath=uri.getPath();
        }
        return realpath;
    }


}
