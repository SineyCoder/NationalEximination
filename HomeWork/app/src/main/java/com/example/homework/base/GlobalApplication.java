package com.example.homework.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.homework.utils.SpUtil;

import androidx.preference.PreferenceManager;

/**
 * author: siney
 * Date: 2020/10/20
 * description:
 */
public class GlobalApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SpUtil.init(PreferenceManager.getDefaultSharedPreferences(context));
    }


    public static Context getContext(){
        return context;
    }

}
