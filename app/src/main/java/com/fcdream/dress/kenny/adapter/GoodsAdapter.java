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

    public GoodsAdapter(Context context, OnItemClickListener listener) {
        super(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof GoodsAdapter.ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            GoodsResult.GoodsInfo item = dataList.get(i);
            Picasso.with(App.getAppInstance())
                    .load(item.img)
                    .resize((int) App.getAppInstance().getResources().getDimension(R.dimen.goods_info_image_width), (int) App.getAppInstance().getResources().getDimension(R.dimen.goods_info_image_height))
                    .centerCrop()
                    .placeholder(R.drawable.default_dress_big)
                    .into(viewHolder.dressImage);
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
}
