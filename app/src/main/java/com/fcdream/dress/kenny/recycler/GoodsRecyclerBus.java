package com.fcdream.dress.kenny.recycler;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.adapter.GoodsAdapter;
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
        recyclerView = view;

        layoutManager = new GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false);
        adapter = new GoodsAdapter(activity, onItemClickListener);
        footView = activity.getLayoutInflater().inflate(R.layout.custom_bottom_progressbar, null);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int space = (int) activity.getResources().getDimension(R.dimen.goods_info_image_space);
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
                outRect.top = space;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLoadMoreView(footView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER);

        recyclerView.setDefaultOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);
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
