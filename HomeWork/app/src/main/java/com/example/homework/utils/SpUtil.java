package com.example.homework.utils;

import android.content.SharedPreferences;

/**
 * author: siney
 * Date: 2020/10/20
 * description:
 */
public class SpUtil {
    private static SharedPreferences mSp;

    public static void init(SharedPreferences sp) {
        mSp = sp;
    }

    public static void saveOrUpdate(String key, String value) {
        mSp.edit().putString(key, value).apply();
    }

    public static String find(String key) {
        return mSp.getString(key, null);
    }

    public static void delete(String key) {
        mSp.edit().remove(key).apply();
    }

    //清除所有缓存
    public static void clearAll() {
        mSp.edit().clear().apply();
    }

    public static boolean has(String key) {
        return mSp.contains(key);
    }


    public static void saveOrUpdateBoolean(String key,boolean flag){
        mSp.edit().putBoolean(key,flag).apply();
    }
    public static boolean findBoolean(String key){
        return mSp.getBoolean(key,false);
    }

    public static boolean findVoiceBoolean(String key){
        return mSp.getBoolean(key,true);
    }
}
