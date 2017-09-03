package com.fcdream.dress.kenny.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.bo.DressItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmdu on 2017/9/3.
 */

public class DressItemAdapter extends RecyclerView.Adapter<DressItemAdapter.ViewHolder> {

    private List<DressItem> dataList = new ArrayList<>();

    @Override
    public DressItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_dress_info, null, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DressItem dressItem = dataList.get(position);
        Picasso.with(App.getAppInstance()).load(dressItem.bigimg).into(holder.dressImage);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView dressImage;
        public RelativeLayout bgLayout;

        public ViewHolder(View view) {
            super(view);
            dressImage = (ImageView) view.findViewById(R.id.dress_item);
            bgLayout = (RelativeLayout) view;
        }
    }

    public List<DressItem> getDataList() {
        return dataList;
    }
}
