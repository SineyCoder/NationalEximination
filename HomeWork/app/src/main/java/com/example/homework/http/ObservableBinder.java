package com.example.homework.http;


import android.content.Context;
import android.util.Log;


import com.example.homework.interf.Callback;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author: siney
 * Date: 2019/7/4
 * description:
 */
public class ObservableBinder<T> {

    private Observer<T> observer;
    private Scheduler subscribeOn;//订阅前的线程
    private Scheduler observeOn;//订阅返回后，观察者的线程
    private Callback<T> callback, afterErrorCallback;
    private Context context;

    public ObservableBinder(Context context){
        this.context = context;
    }

    public ObservableBinder observer(Observer<T> observer){
        this.observer = observer;
        return this;
    }

    public ObservableBinder callback(Callback<T> callback){
        this.callback = callback;
        return this;
    }

    public ObservableBinder subscribeOn(Scheduler subscribeOn){
        this.subscribeOn = subscribeOn;
        return this;
    }

    public ObservableBinder observeOn(Scheduler observeOn){
        this.observeOn = observeOn;
        return this;
    }

    public void bind(Observable<T> observable){
        try {
            if(callback == null){
                throw new Exception("未设置回调接口");
            }else if(observable == null){
                throw new Exception("observable为空");
            }
            if(subscribeOn != null){
                observable = observable.subscribeOn(subscribeOn);
            }else{
                observable = observable.subscribeOn(Schedulers.io());
            }
            if(observeOn != null){
                observable = observable.observeOn(observeOn);
            }else{
                observable = observable.observeOn(AndroidSchedulers.mainThread());
            }

            if(observer != null){
                observable.subscribe(observer);
            }else{
                observable.subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("TTT", "onSubscribe");
                    }

                    @Override
                    public void onNext(T t) {
                        Log.e("NEWHTTP", "onNext");
                        callback.onData(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //这里写其他异常
                        Log.e("NEWHTTP", "otherException,"+e.getMessage());
                        onComplete();
                    }

                    @Override
                    public void onComplete() {
                        Log.e("NEWHTTP", "onComplete");
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Context getContext(){
        return context;
    }

}
