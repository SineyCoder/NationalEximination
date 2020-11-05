package com.example.homework.http;

import com.example.homework.http.interf.ExamApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * author: siney
 * Date: 2020/10/23
 * description:
 */
public class ApiFactory {

    private static ConcurrentHashMap<Class<?>, Object> apis = new ConcurrentHashMap<>();

    private static Object getBaseApi(String url, Class<?> clazz){
        Object api;
        if((api = apis.get(clazz)) == null){
            synchronized (clazz){
                if((api = apis.get(clazz)) == null){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(200, TimeUnit.SECONDS)
                            .readTimeout(200, TimeUnit.SECONDS)
                            .writeTimeout(200, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true);
                    api = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(FastJsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(builder.build())
                            .build()
                            .create(clazz);
                    apis.put(clazz, api);
                }
            }
        }
        return api;
    }

    public static <T> T getApi(Class<T> clazz){
        if(ExamApi.class.equals(clazz)){
            return (T) getBaseApi(Url.EXAM_URL, clazz);
        }
        return null;
    }

}
