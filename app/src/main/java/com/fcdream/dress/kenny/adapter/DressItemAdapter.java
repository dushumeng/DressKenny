package com.fcdream.dress.kenny.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.bo.DressItem;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmdu on 2017/9/3.
 */

public class DressItemAdapter extends UltimateViewAdapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<DressItem> dataList = new ArrayList<>();

    private Context context;

    private OnItemClickListener listener;

    private int selectPosition;

    public DressItemAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_dress_info, parent, false);
        ViewHolder vh = new ViewHolder(view, this);
        return vh;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getAdapterItemCount() {
        return dataList.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return -1;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        DressItem item = dataList.get(position);
        if (listener != item) {
            listener.onItemClick(view, position, item);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder mViewHolder = (ViewHolder) holder;
            DressItem dressItem = dataList.get(position);
            Picasso.with(App.getAppInstance())
                    .load(dressItem.img)
                    .resize((int) App.getAppInstance().getResources().getDimension(R.dimen.list_page_content_dress_item_width)
                            , (int) App.getAppInstance().getResources().getDimension(R.dimen.list_page_content_dress_item_height))
                    .centerCrop()
                    .placeholder(R.drawable.default_dress_big)
                    .into(mViewHolder.dressImage);
            mViewHolder.dressImage.setTag(position);
        }
    }

    public static class ViewHolder extends UltimateRecyclerviewViewHolder {
        public ImageView dressImage;
        public ImageView bgImage;

        public ViewHolder(View view, View.OnClickListener clickListener) {
            super(view);
            dressImage = (ImageView) view.findViewById(R.id.dress_item);
            dressImage.setOnClickListener(clickListener);
            bgImage = (ImageView) view.findViewById(R.id.dress_item_bg);
        }
    }

    public List<DressItem> getDataList() {
        return dataList;
    }
}
