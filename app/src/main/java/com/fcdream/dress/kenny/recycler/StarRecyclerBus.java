package com.fcdream.dress.kenny.recycler;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.fcdream.dress.kenny.adapter.StarAdapter;
import com.fcdream.dress.kenny.bo.api.StarResult;
import com.fcdream.dress.kenny.retrofit.api.StarBus;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DefaultObserver;

/**
 * Created by shmdu on 2017/9/7.
 */

public class StarRecyclerBus extends MyRecyclerBus<LinearLayoutManager, String> {

    @Override
    public void init(Activity activity, UltimateRecyclerView view, OnItemClickListener onItemClickListener) {
        recyclerView = view;
        layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new StarAdapter(activity, onItemClickListener);
        footView = activity.getLayoutInflater().inflate(R.layout.custom_bottom_progressbar, null);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLoadMoreView(null);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER);

        recyclerView.setDefaultOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void dealLoadData(int pageIndex, String obj) {
        StarBus.getList(obj, pageIndex + 1, pageNum, new DefaultObserver<StarResult>() {
            @Override
            public void onNext(@NonNull StarResult starResult) {
                dealLoadDataFinish(pageIndex, starResult.queryInfo.count, starResult.starInfoList);
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
