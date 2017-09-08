package com.fcdream.dress.kenny.retrofit.api;

import com.fcdream.dress.kenny.bo.api.GoodsResult;
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
 * date：2017/9/8
 * description：
 */
public class GoodsBus {

    public static void getList(String shopId, int pageIndex, int pageNum, final DefaultObserver<GoodsResult> defaultObserver) {
        RetrofitFactory.i().create(GoodsBus.Api.class)
                .getList(shopId, pageIndex, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(defaultObserver);
    }

    public interface Api {
        @GET("dress/goods.pl")
        Observable<GoodsResult> getList(@Query("shop_id") String shopId, @Query("p") int pageIndex, @Query("pn") int pageNum);
    }
}
