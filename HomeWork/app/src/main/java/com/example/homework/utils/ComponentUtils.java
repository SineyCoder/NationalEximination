package com.example.homework.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class ComponentUtils {

    /**
     * 显示Toast
     * @param context 当前活动
     * @param content 内容
     */
    public static void showToast(Context context, String content){
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
