package com.example.homework.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import java.util.Stack;

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    /**
     * 单一实例
     */
    public static AppManager getInstance(){
        if(instance == null){
            instance = new AppManager();
        }
        return  instance;
    }

    public void addActivity(Activity activty){
        if(activityStack == null){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activty);
    }

    /**
     * 在堆栈中移除Activity,在OnDestroy中调用
     * */
    public void removeActivity(Activity activity){
        if(activityStack!=null){
            activityStack.remove(activity);
        }
    }

    public int getSize(){
        return activityStack.size();
    }

    /**
     * 获取当前activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity(){
        return  activityStack.lastElement();
    }

    /**
     * 获取指定的Activity
     */
    public <T> T getActivity(Class<T> cls){
        if(activityStack!=null){
            for( Activity activity : activityStack){
                if(activity.getClass().equals(cls)){
                    return (T) activity;
                }
            }
        }
        return null;
    }

    public Activity getActivity(int index){
        if(index < activityStack.size()){
            return activityStack.get(index);
        }
        return null;
    }

    /**
     * 结束当前Activity
     */
    public void finishActivtiy(){
        finishActivity(activityStack.lastElement());
    }

    /**
     * 结束指定的activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if(activity != null && !activity.isFinishing()){
            activityStack.remove(activity);
            activity.finish();
            /*activity = null;*/
        }
    }

    /**
     * 结束指定类名的Activity
     * @param cls
     */
    public void finishActivity(Class<?> cls){
        for(Activity activity :activityStack){
            if(activity.getClass().equals(cls)){
                finishActivity(activity);
                break;
            }
        }
    }
    /**
     * 结束所有Activity
     * 可以先finishAll，然后Intent启动一个app
     */
    public void finishAllActivity(){
        for(int i = activityStack.size()-1;i>0; i-- ){
            if(activityStack.get(i) != null){
                finishActivity(activityStack.get(i));
            }
        }
        finishActivity(activityStack.get(0));
        activityStack.clear();
    }

    public void finishAllExceptActivity(Class<?> activity){
        for(int i = activityStack.size() - 1;i >= 0;i--){
            if(activityStack.get(i) != null && !(activityStack.get(i).getClass().equals(activity))){
                finishActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 获取当前堆栈中有多少Activity;
     * @return
     */
    public int getTotalActivity(){
     return activityStack.size();
    }
    /**
     * 退出应用程序
     */
    public void exitApp(){
        try{
            finishAllActivity();
            Process.killProcess(Process.myPid());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void finishToTopActivity(Class clazz){
        for(int i = activityStack.size() - 1;i >= 0;i--){
            Activity activity = activityStack.get(i);
            if(!activity.getClass().equals(clazz)){
                finishActivity(activity);
            }else{
                return;
            }
        }
    }

}
