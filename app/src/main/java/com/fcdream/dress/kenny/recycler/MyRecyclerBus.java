package com.fcdream.dress.kenny.recycler;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.fcdream.dress.kenny.adapter.MyUltimateViewAdapter;
import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

/**
 * Created by shmdu on 2017/9/7.
 */

public abstract class MyRecyclerBus<K, V> implements SwipeRefreshLayout.OnRefreshListener, UltimateRecyclerView.OnLoadMoreListener {

    private static final int STATE_START = 1;
    private static final int STATE_STOP = 2;
    private static final int STATE_DESTROY = 3;

    public UltimateRecyclerView recyclerView;

    public MyUltimateViewAdapter adapter;

    /**
     * 页索引
     */
    protected int pageIndex;
    /**
     * 页数
     */
    protected int pageCount;
    /**
     * 数据总长度
     */
    protected int allCount;
    /**
     * 每页数据长度
     */
    public int pageNum = 12;

    public K layoutManager;

    public View footView;

    public V userData;

    private boolean isLoading = false;

    protected int currentState = STATE_START;

    abstract public void init(Activity activity, UltimateRecyclerView view, OnItemClickListener onItemClickListener);

    public void loadData() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        dealLoadData(0, userData);
    }

    public void loadNext() {
        if (isLoading || pageIndex >= pageCount) {
            return;
        }
        isLoading = false;
        dealLoadData(pageIndex + 1, userData);
    }

    abstract protected void dealLoadData(int pageIndex, V obj);

    public void restPage(int pageIndex, int allCount) {
        this.pageIndex = pageIndex;
        this.allCount = allCount;
        this.pageCount = (allCount % pageNum == 0) ? (allCount / pageNum) : (allCount / pageNum + 1);
        if (footView != null) {
            if (this.pageIndex >= this.pageCount) {
                recyclerView.disableLoadmore();
            } else {
                recyclerView.reenableLoadmore();
            }
        }
    }

    public void dealLoadDataFinish(int pageIndex, int allCount, List dataList) {
        isLoading = false;
        recyclerView.setRefreshing(false);
        if (currentState != STATE_START) {
            return;
        }
        restPage(pageIndex, allCount);
        List list = adapter.getDataList();
        if (pageIndex == 0) {
            list.clear();
        }
        if (dataList != null && dataList.size() > 0) {
            list.addAll(dataList);
            adapter.notifyDataSetChanged();
            if (pageIndex == 0) {
                recyclerView.mRecyclerView.smoothScrollToPosition(0);
            }
        }
    }

    public void dealLoadDataError() {
        isLoading = false;
        recyclerView.setRefreshing(false);
        if (currentState != STATE_START) {
            return;
        }
    }

    public void onStop() {
        currentState = STATE_STOP;
    }

    public void onDestroy() {
        currentState = STATE_DESTROY;
        if (adapter != null) {
            adapter.release();
        }
    }

    public void onStart() {
        currentState = STATE_START;
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        loadNext();
    }

    public int getDataListSize() {
        return adapter == null ? 0 : adapter.getDataList().size();
    }

    public Object getData(int position) {
        int dataSize = getDataListSize();
        if (position >= dataSize) {
            return null;
        }
        return adapter.getDataList().get(position);
    }


}
