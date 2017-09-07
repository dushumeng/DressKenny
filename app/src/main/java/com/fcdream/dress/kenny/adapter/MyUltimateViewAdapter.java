package com.fcdream.dress.kenny.adapter;

import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmdu on 2017/9/7.
 */

public abstract class MyUltimateViewAdapter<T> extends UltimateViewAdapter<RecyclerView.ViewHolder> {

    final protected List<T> dataList = new ArrayList<T>();

    public List<T> getDataList() {
        return dataList;
    }

    public void release() {
        dataList.clear();
    }
}
