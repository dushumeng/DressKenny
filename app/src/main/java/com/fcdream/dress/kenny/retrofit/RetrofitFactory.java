package com.fcdream.dress.kenny.retrofit;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/6
 * description：
 */
public class RetrofitFactory<T> {

    public static final String TYPE_BASE = "type_base";

    private static final String BASE_URL = "http://47.94.0.142/cgi/";

    private static final RetrofitFactory i = new RetrofitFactory();

    private Map<String, Retrofit> retrofitMap = new HashMap<>();

    private RetrofitFactory() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        retrofitMap.put(TYPE_BASE, retrofit);
    }

    public static RetrofitFactory i() {
        return i;
    }

    public synchronized Object create(Class<T> clazz) {
        return retrofitMap.get(TYPE_BASE).create(clazz);
    }
}
