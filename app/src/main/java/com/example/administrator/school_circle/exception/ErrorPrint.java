package com.example.administrator.school_circle.exception;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Created by BigGod on 2017-05-05.
 */
public class ErrorPrint {
    public static void printError(String errorStr, Context context){
        Toasty.error(context,errorStr,Toast.LENGTH_LONG,true).show();
    }

    public static void printOutTimeError( Context context){
        Toasty.error(context,"服务器请求超时",Toast.LENGTH_LONG,true).show();
    }
}
