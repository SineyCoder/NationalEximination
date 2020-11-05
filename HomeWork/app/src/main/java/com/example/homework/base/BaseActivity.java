package com.example.homework.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.homework.utils.ComponentUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    private AppManager appManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止旋转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        appManager = AppManager.getInstance();
        appManager.addActivity(this);
        Log.e("TAG", getLocalClassName() + "加入list");
    }

    @Override
    protected void onDestroy() {
        appManager.removeActivity(this);
        Log.e("TAG", getLocalClassName() + "移除list");
        super.onDestroy();
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if(AppManager.getInstance().getSize() == 1){
            long cur = System.currentTimeMillis();
            if((cur - exitTime > 2000)){
                ComponentUtils.showToast(this, "再按一次退出程序");
                exitTime = cur;
                return;
            }else{
                moveTaskToBack(false);
                return;
            }
        }
        super.onBackPressed();
    }

}
