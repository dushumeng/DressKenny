package com.fcdream.dress.kenny.recycler;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.fcdream.dress.kenny.adapter.StarAdapter;
import com.fcdream.dress.kenny.bo.StarResult;
import com.fcdream.dress.kenny.retrofit.api.StarBus;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shmdu on 2017/9/7.
 */

public class StarRecyclerBus extends MyRecyclerBus<LinearLayoutManager, String>
        implements SwipeRefreshLayout.OnRefreshListener, UltimateRecyclerView.OnLoadMoreListener {

    public String currentSearchKey;

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
        StarBus.queryStartList(obj, pageIndex + 1, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<StarResult>() {
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

    @Override
    public void onRefresh() {
        loadData(currentSearchKey);
    }

    @Override
    public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        loadNext(currentSearchKey);
    }
}
