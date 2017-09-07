package com.fcdream.dress.kenny.retrofit.api;

import com.fcdream.dress.kenny.bo.StarResult;
import com.fcdream.dress.kenny.retrofit.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public static Observable<StarResult> queryStartList(String searchKey, int pageIndex, int pageCount) {
        return ((StarBus.Api) (RetrofitFactory.i().create(StarBus.Api.class)))
                .get(searchKey, pageIndex, pageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface Api {
        @GET("dress/star.pl")
        Observable<StarResult> get(@Query("q") String searchKey, @Query("p") int pageIndex, @Query("pn") int pageCount);
    }
}