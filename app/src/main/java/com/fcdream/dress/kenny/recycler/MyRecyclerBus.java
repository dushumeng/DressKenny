package com.fcdream.dress.kenny.recycler;

import android.app.Activity;
import android.view.View;

import com.fcdream.dress.kenny.adapter.MyUltimateViewAdapter;
import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

/**
 * Created by shmdu on 2017/9/7.
 */

public abstract class MyRecyclerBus<K, V> {

    public UltimateRecyclerView recyclerView;

    public MyUltimateViewAdapter adapter;

    public int pageIndex;

    public int pageCount;

    public int allCount;

    public int pageNum = 12;

    public K layoutManager;

    public View footView;

    private boolean isLoading = false;

    abstract public void init(Activity activity, UltimateRecyclerView view, OnItemClickListener onItemClickListener);

    public void loadData(V object) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        dealLoadData(0, object);
    }

    public void loadNext(V object) {
        if (isLoading || pageIndex >= pageCount) {
            return;
        }
        isLoading = false;
        dealLoadData(pageIndex + 1, object);
    }

    abstract protected void dealLoadData(int pageIndex, V obj);

    public void restPage(int pageIndex, int allCount) {
        this.pageIndex = pageIndex;
        this.allCount = allCount;
        this.pageCount = (allCount % pageNum == 0) ? (allCount / pageNum) : (allCount / pageNum + 1);
        if (footView != null) {
            if (this.pageIndex >= this.pageCount) {
                footView.setVisibility(View.GONE);
            } else {
                footView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void dealLoadDataFinish(int pageIndex, int allCount, List dataList) {
        restPage(pageIndex, allCount);
        isLoading = false;
        recyclerView.setRefreshing(false);
        List list = adapter.getDataList();
        if (pageIndex == 0) {
            list.clear();
        }
        if (dataList != null && dataList.size() > 0) {
            list.addAll(dataList);
            adapter.notifyDataSetChanged();
        }
    }

    public void dealLoadDataError() {
        recyclerView.setRefreshing(false);
        isLoading = false;
    }
}
