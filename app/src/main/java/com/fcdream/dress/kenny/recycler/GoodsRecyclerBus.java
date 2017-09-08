package com.fcdream.dress.kenny.recycler;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;

import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.fcdream.dress.kenny.bo.api.GoodsResult;
import com.fcdream.dress.kenny.retrofit.api.GoodsBus;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DefaultObserver;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/8
 * description：
 */
public class GoodsRecyclerBus extends MyRecyclerBus<GridLayoutManager, String> {

    @Override
    public void init(Activity activity, UltimateRecyclerView view, OnItemClickListener onItemClickListener) {

    }

    @Override
    protected void dealLoadData(int pageIndex, String userData) {
        GoodsBus.getList(userData, pageIndex, pageNum
                , new DefaultObserver<GoodsResult>() {
                    @Override
                    public void onNext(@NonNull GoodsResult goodsResult) {
                        dealLoadDataFinish(pageIndex, goodsResult.queryInfo.count, goodsResult.goodsInfoList);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        dealLoadDataError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
