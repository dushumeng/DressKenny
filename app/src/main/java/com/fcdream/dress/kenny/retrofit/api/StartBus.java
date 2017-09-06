package com.fcdream.dress.kenny.retrofit.api;

import com.fcdream.dress.kenny.bo.StarResult;
import com.fcdream.dress.kenny.retrofit.RetrofitFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/6
 * description：
 */
public class StartBus {

    public void queryStarList(String searchKey, int pageIndex, int pageCount) {
        ((StartBus.Api) (RetrofitFactory.i().create(StartBus.Api.class))).get(searchKey, pageIndex, pageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<StarResult>() {

                    @Override
                    public void onNext(@NonNull StarResult starResult) {

                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface Api {
        @GET("dress/star.pl")
        Observable<StarResult> get(@Query("q") String searchKey, @Query("p") int pageIndex, @Query("pn") int pageCount);
    }
}