package com.fcdream.dress.kenny.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.bo.api.GoodsResult;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/8
 * description：
 */
public class GoodsAdapter extends MyUltimateViewAdapter<GoodsResult.GoodsInfo> {

    private OnItemClickListener listener;

    public GoodsAdapter(Context context, OnItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof GoodsAdapter.ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            GoodsResult.GoodsInfo item = dataList.get(i);
            Picasso.with(App.getAppInstance())
                    .load(item.img)
                    .placeholder(R.drawable.default_dress_big)
                    .into(viewHolder.dressImage);
            viewHolder.dressImage.setTag(i);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_goods_info, parent, false);
        ViewHolder vh = new ViewHolder(view, this);
        return vh;
    }

    public static class ViewHolder extends UltimateRecyclerviewViewHolder {
        final public ImageView dressImage;

        public ViewHolder(View view, View.OnClickListener clickListener) {
            super(view);
            dressImage = (ImageView) view.findViewById(R.id.dress_item);
            dressImage.setOnClickListener(clickListener);
        }
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            int position = (int) view.getTag();
            listener.onItemClick(view, position, dataList.get(position));
            return;
        }
        super.onClick(view);
    }
}
