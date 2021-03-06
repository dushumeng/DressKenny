package com.fcdream.dress.kenny.retrofit.api;

import com.fcdream.dress.kenny.bo.api.StarResult;
import com.fcdream.dress.kenny.retrofit.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/6
 * description：
 */
public class StarBus {

    public static void getList(String searchKey, int pageIndex, int pageNum, final DefaultObserver<StarResult> defaultObserver) {
        RetrofitFactory.i().create(StarBus.Api.class)
                .getList(searchKey, pageIndex, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(defaultObserver);
    }

    public interface Api {
        @GET("dress/star.pl")
        Observable<StarResult> getList(@Query("q") String searchKey, @Query("p") int pageIndex, @Query("pn") int pageNum);
    }
}