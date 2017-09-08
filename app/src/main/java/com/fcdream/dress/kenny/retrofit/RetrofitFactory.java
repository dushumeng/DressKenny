package com.fcdream.dress.kenny.retrofit;

import android.util.Log;

import com.fcdream.dress.kenny.log.MyLog;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/6
 * description：
 */
public class RetrofitFactory {

    public static final String TAG = RetrofitFactory.class.getSimpleName();

    public static final String TYPE_BASE = "type_base";

    private static final Map<String, String> baseUrlMap = new HashMap<>();

    static {
        baseUrlMap.put(TYPE_BASE, "http://47.94.0.142/cgi/");
    }

    private static final RetrofitFactory i = new RetrofitFactory();

    private Map<String, Retrofit> retrofitMap = new HashMap<>();

    private OkHttpClient okHttpClient;

    private RetrofitFactory() {
        init();
    }

    public static RetrofitFactory i() {
        return i;
    }

    private void init() {
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor
                = new HttpLoggingInterceptor(message -> MyLog.d(TAG, "OkHttpClient:" + message));
        if (MyLog.level == Log.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(10, TimeUnit.SECONDS);
        okHttpClient = httpClientBuilder.build();

        for (String key : baseUrlMap.keySet()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())//解析方法
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(baseUrlMap.get(key))
                    .build();
            retrofitMap.put(key, retrofit);
        }
    }

    public <T> T create(String type, Class<T> clazz) {
        return retrofitMap.get(type).create(clazz);
    }

    public <T> T create(Class<T> clazz) {
        return create(TYPE_BASE, clazz);
    }
}
