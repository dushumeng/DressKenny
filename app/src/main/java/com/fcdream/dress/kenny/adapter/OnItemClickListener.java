package com.fcdream.dress.kenny.adapter;

import android.view.View;

/**
 * Created by shmdu on 2017/9/5.
 */

public interface OnItemClickListener<T> {
    void onItemClick(View view, int position, T object);
}
